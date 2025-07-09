import spotipy
from spotipy.oauth2 import SpotifyOAuth
import pandas as pd
import json
from kafka import KafkaProducer
from datetime import datetime
import uuid
import time

# Spotify Configuration
CLIENT_ID = "1df83290472147eba0a2586f27a5e645"
CLIENT_SECRET = "3c42466c860a461da900f8b7d6551258"
REDIRECT_URI = "http://127.0.0.1:8888/callback"

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS = 'localhost:9092'
KAFKA_TOPIC = 'catalogue-events'

# Initialize Kafka Producer
def create_kafka_producer():
    try:
        producer = KafkaProducer(
            bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS,
            value_serializer=lambda v: json.dumps(v).encode('utf-8'),
            key_serializer=lambda k: str(k).encode('utf-8')
        )
        print("Kafka producer connected successfully")
        return producer
    except Exception as e:
        print(f"Error connecting to Kafka: {e}")
        return None

# Kafka event sender
def send_catalogue_event(event_type, entity_id, message, kafka_producer):
    try:
        if not event_type:
            print("ERREUR: event_type est null ou vide, événement non envoyé")
            return
        
        formatted_message = message
        if event_type in ["ALBUM_SCRAPED", "SINGLE_SCRAPED"]:
            if isinstance(message, dict):
                formatted_message = f"{message.get('Nom', '')}|{message.get('Cover', '')}|{message.get('Type', 'ALBUM')}"
        elif event_type == "TRACK_SCRAPED":
            if isinstance(message, dict):
                formatted_message = f"{message.get('Titre', '')}|{message.get('Popularity', 0)}|{message.get('Duration (ms)', 0)}|{message.get('Explicit', False)}|{message.get('Preview URL', '')}|{message.get('Cover', '')}|{message.get('Type', 'ALBUM')}"
        elif event_type == "TOP_TRACKS_SCRAPED":
            if isinstance(message, list):
                formatted_message = "|".join([f"{track.get('Titre', '')}|{track.get('Popularity', 0)}|{track.get('Duration (ms)', 0)}|{track.get('Explicit', False)}|{track.get('Preview URL', '')}|{track.get('Cover', '')}|ALBUM" for track in message])

        event = {
            "eventType": event_type,
            "entityId": entity_id,
            "message": formatted_message,
            "timestamp": datetime.now().isoformat()
        }
        
        if kafka_producer is None:
            raise Exception("Le producteur Kafka n'est pas initialisé")
            
        kafka_producer.send(KAFKA_TOPIC, value=event)
        kafka_producer.flush()
        print(f"Événement envoyé : {event_type} pour {entity_id}")
    except Exception as e:
        print(f"Erreur lors de l'envoi de l'événement : {str(e)}")
        
# Initialize Spotify
sp = spotipy.Spotify(
    auth_manager=SpotifyOAuth(
        client_id=CLIENT_ID,
        client_secret=CLIENT_SECRET,
        redirect_uri=REDIRECT_URI,
        scope=["user-library-read", "playlist-read-private"]
    ),
    requests_timeout=60
)

def get_artist_id(artist_name):
    """Recherche l'ID d'un artiste sur Spotify"""
    result = sp.search(q=artist_name, type="artist", limit=1)
    if result['artists']['items']:
        return result['artists']['items'][0]['id']
    return None

def get_albums(artist_id, album_type="album", kafka_producer=None):
    """
    Récupère les albums ou singles d'un artiste avec leurs détails
    album_type peut être 'album' ou 'single'
    """
    albums_raw = sp.artist_albums(artist_id, album_type=album_type)
    albums_list = []
    artist_data = sp.artist(artist_id)
    artist_genres = artist_data.get('genres', [])
    artist_name = artist_data.get('name', 'Unknown Artist')
    
    for album in albums_raw['items']:
        album_full = sp.album(album['id'])
        album_details = {
            "Nom": album_full['name'],
            "Album ID": album_full['id'],
            "Release Date": album_full.get('release_date', 'Inconnue'),
            "Cover": album_full['images'][0]['url'] if album_full.get('images') else None,
            "Genres": artist_genres,
            "Type": album_type.upper(),  # Ajout du type (ALBUM ou SINGLE)
            "Tracks": []
        }
        
        # Envoi événement Kafka pour album/single
        if kafka_producer:
            send_catalogue_event(
                "ALBUM_SCRAPED" if album_type == "album" else "SINGLE_SCRAPED",  # Différencier album et single
                album_full['id'],
                album_details,
                kafka_producer
            )
            time.sleep(0.1)
            
        tracks_raw = sp.album_tracks(album_full['id'])
        for track in tracks_raw['items']:
            track_full = sp.track(track['id'])
            track_details = {
                "Titre": track_full['name'],
                "Track ID": track_full['id'],
                "Popularity": track_full.get('popularity'),
                "Duration (ms)": track_full.get('duration_ms'),
                "Explicit": track_full.get('explicit'),
                "Preview URL": track_full.get('preview_url'),
                "Track Number": track_full.get('track_number'),
                "Featurings": [artist['name'] for artist in track_full['artists'] if artist['id'] != artist_id],
                "Cover": album_details["Cover"],
                "Release Date": album_details["Release Date"],
                "Type": album_type.upper()  # Ajout du type pour les tracks aussi
            }
            album_details["Tracks"].append(track_details)
            
            # Envoi événement Kafka pour piste (album ou single)
            if kafka_producer:
                send_catalogue_event(
                    "TRACK_SCRAPED",
                    track_full['id'],
                    track_details,  # Envoyer les détails de la piste directement
                    kafka_producer
                )
                time.sleep(0.1)
                
        albums_list.append(album_details)
    return albums_list

def get_playlists(artist_name, kafka_producer=None):
    """Récupère les playlists contenant l'artiste"""
    results = sp.search(q=artist_name, type="playlist", limit=5)
    items = results.get('playlists', {}).get('items', [])
    playlists = []
    
    if items:
        playlists = [playlist.get('name', 'No Name') for playlist in items if playlist is not None]
        
        # Envoi événement Kafka pour playlists
        if kafka_producer and playlists:
            send_catalogue_event(
                "PLAYLISTS_SCRAPED",
                artist_name,
                f"Found {len(playlists)} playlists contenant {artist_name}",
                kafka_producer
            )
    else:
        print(f"Aucune playlist trouvée pour l'artiste {artist_name}.")
    
    return playlists

def get_top_tracks(artist_id, kafka_producer=None):
    """Récupère les morceaux les plus populaires d'un artiste"""
    tracks = sp.artist_top_tracks(artist_id)['tracks']
    top_tracks = [
        {
            "Titre": track['name'],
            "Track ID": track['id'],
            "Popularity": track.get('popularity'),
            "Collaborations": [artist['name'] for artist in track['artists'] if artist['id'] != artist_id]
        }
        for track in tracks
    ]
    
    # Envoi événement Kafka pour top tracks
    if kafka_producer and top_tracks:
        artist_data = sp.artist(artist_id)
        artist_name = artist_data.get('name', 'Unknown Artist')
        send_catalogue_event(
            "TOP_TRACKS_SCRAPED",
            artist_id,
            f"Scraped top {len(top_tracks)} tracks for {artist_name}",
            kafka_producer
        )
    
    return top_tracks

def get_metadata(artist_name, kafka_producer=None):
    """
    Récupère toutes les métadonnées d'un artiste, incluant :
    - Albums
    - Singles  <----- Partie SINGLE bien intégrée ici
    - Playlists
    - Top Tracks
    """
    if kafka_producer:
        send_catalogue_event(
            "SCRAPING_STARTED",
            artist_name,
            f"Started scraping data for artist: {artist_name}",
            kafka_producer
        )
    
    artist_id = get_artist_id(artist_name)
    if not artist_id:
        if kafka_producer:
            send_catalogue_event(
                "SCRAPING_ERROR",
                artist_name,
                f"Artist not found: {artist_name}",
                kafka_producer
            )
        return {"Erreur": "Artiste non trouvé"}
    
    metadata = {
        "Artiste": artist_name,
        "Albums": get_albums(artist_id, "album", kafka_producer),     # Albums classiques
        "Singles": get_albums(artist_id, "single", kafka_producer),   # Singles ici !
        "Playlists": get_playlists(artist_name, kafka_producer),
        "Top Tracks": get_top_tracks(artist_id, kafka_producer)
    }
    
    if kafka_producer:
        total_albums = len(metadata["Albums"])
        total_singles = len(metadata["Singles"])
        total_tracks = sum(len(album["Tracks"]) for album in metadata["Albums"] + metadata["Singles"])
        
        send_catalogue_event(
            "SCRAPING_COMPLETED",
            artist_name,
            f"Completed scraping for {artist_name}: {total_albums} albums, {total_singles} singles, {total_tracks} tracks",
            kafka_producer
        )
    
    return metadata

def save_to_csv(metadata, filename="metadata.csv"):
    """
    Sauvegarde les métadonnées dans un fichier CSV.
    Les listes imbriquées sont converties en JSON.
    """
    rows = []
    for key, value in metadata.items():
        if isinstance(value, list):
            for item in value:
                if isinstance(item, dict):
                    rows.append([key, json.dumps(item, ensure_ascii=False)])
                else:
                    rows.append([key, str(item)])
        else:
            rows.append([key, str(value)])
    
    df = pd.DataFrame(rows, columns=["Type", "Valeur"])
    df.to_csv(filename, index=False, encoding='utf-8-sig')
    print(f"Données sauvegardées dans {filename}")

def main():
    kafka_producer = create_kafka_producer()
    if kafka_producer is None:
        print("Impossible de continuer sans producteur Kafka")
        return
    
    artist_name = input("Entrez le nom de l'artiste : ")
    metadata = get_metadata(artist_name, kafka_producer)
    
    # Sauvegarder localement
    save_to_csv(metadata)

if __name__ == "__main__":
    main()