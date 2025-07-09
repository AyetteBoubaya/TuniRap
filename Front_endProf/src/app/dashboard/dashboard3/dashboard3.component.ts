import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

interface EventType {
  name: string;
  color: string;
}

interface Event {
  id: number;
  title: string;
  image: string;
  type: EventType;
  date: Date;
  location: string;
  remainingTickets: number;
  totalTickets: number;
  price: number;
  organizer: string;
  time: string;
  description: string;
}

@Component({
  selector: 'app-dashboard3',
  templateUrl: './dashboard3.component.html',
  styleUrls: ['./dashboard3.component.scss']
})
export class Dashboard3Component implements OnInit {

  
 collaborations = [
    {
      id: 1,
      senderName: "Samara",
      receiverId: 20,
      collaborationType: 'FEAT',
      subject: 'Nouvelle Collaboration Feat',
      description: 'Proposition pour un feat sur le prochain album.',
      status: 'PENDING',
      creationDate: '2025-06-02 15:30'
    },
    {
      id: 2,
      senderName: "Zebra",
      receiverId: 20,
      collaborationType: 'EVENT',
      subject: 'Participation Événement',
      description: 'Invitation à un concert.',
      status: 'ACCEPTED',
      creationDate: '2025-05-20 18:00'
    },
    {
      id: 3,
      senderName: "Nordo",
      receiverName: "",
      collaborationType: 'FEAT',
      subject: 'Duo Spécial',
      description: 'Proposition de feat pour un clip.',
      status: 'REJECTED',
      creationDate: '2025-05-10 14:00'
    }
  ];

  respond(collaboration: any, accepted: boolean) {
    collaboration.status = accepted ? 'ACCEPTED' : 'REJECTED';
  }

  // Fonction pour formater la date
  getFormattedDate(date: Date): string {
    return date.toLocaleDateString('fr-FR') + ' ' + date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' });
  }


  isFormValid(): boolean {
    return (
      this.senderId > 0 &&
      this.receiverId > 0 &&
      this.type !== '' &&
      this.subject.trim().length > 0 &&
      this.description.trim().length > 0
    );
  }

  submitCollaboration() {
    if (!this.isFormValid()) {
      alert('Merci de remplir tous les champs correctement.');
      return;
    }

    const payload = {
      senderId: this.senderId,
      receiverId: this.receiverId,
      type: this.type,
      subject: this.subject,
      description: this.description
    };

    this.http.post('/api/collaborations', payload).subscribe({
      next: () => {
        alert('Demande de collaboration envoyée avec succès !');
        // Reset formulaire après envoi si tu veux
      },
      error: (error) => {
        console.error('Erreur lors de l’envoi:', error);
        alert('Erreur lors de l’envoi de la demande.');
      }
    });
  }
onSubmit() {
throw new Error('Method not implemented.');
}
senderId: number = 101;       // Exemple Balti
  receiverId: number = 102;     // Exemple Kafon
  type: string = 'FEATURE';     // Par défaut "Feat"
  subject: string = 'Nouveau morceau';
  description: string = 'Collaboration sur un single hip-hop tunisien';
  events: Event[] = [
    {
      id: 1,
      title: 'Festival International de Carthage',
      image: 'assets/img/events/concert1.jpg',
      type: { name: 'Concert', color: 'primary' },
      date: new Date('2025-07-15'),
      location: 'Théâtre Antique de Carthage, Tunis',
      remainingTickets: 42,
      totalTickets: 500,
      price: 25,
      organizer: 'Association Culturelle de Carthage',
      time: '20:00 - 23:00',
      description: 'Un concert exceptionnel avec les plus grandes stars de la musique tunisienne.'
    },
    {
      id: 2,
      title: 'Jazz à Hammamet',
      image: 'assets/img/events/images.jpg',
      type: { name: 'Festival', color: 'danger' },
      date: new Date('2025-07-20'),
      location: 'Centre Culturel Hammamet',
      remainingTickets: 80,
      totalTickets: 300,
      price: 35,
      organizer: 'Jazz Hammamet',
      time: '21:00 - 23:30',
      description: 'Festival incontournable du jazz avec des artistes de renommée mondiale.'
    }
  ];

  selectedEventId: number | null = null;
  participationName = '';
  participationMessage = '';

  // Gestion albums, discographies, playlists, tracks avec types forts
  albumRequest: Record<string, any> = {};
  albums: any[] = [];

  discographieRequest: Record<string, any> = {};
  discographies: any[] = [];

  playlistRequest: Record<string, any> = {};
  playlists: any[] = [];

  trackRequest: Record<string, any> = {};
  tracks: any[] = [];

  active: any;
collabForm: any;
rappers: any;
loading: any;
responseMessage: any;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadAlbums();
    this.loadDiscographies();
    this.loadPlaylists();
    this.loadTracks();
  }

  // Méthodes Participation
  openParticipation(eventId: number): void {
    this.selectedEventId = eventId;
  }

  submitParticipation(): void {
    if (!this.participationName.trim() || !this.participationMessage.trim()) {
      alert('Veuillez remplir votre nom et votre message.');
      return;
    }
    console.log(`Participation à l'événement ${this.selectedEventId}`);
    console.log(`Nom : ${this.participationName}`);
    console.log(`Message : ${this.participationMessage}`);
    // Ici, tu peux appeler une API si besoin pour envoyer la participation

    // Reset formulaire
    this.selectedEventId = null;
    this.participationName = '';
    this.participationMessage = '';
  }

  cancelParticipation(): void {
    this.selectedEventId = null;
    this.participationName = '';
    this.participationMessage = '';
  }

  // Album Methods
  onAlbumSubmit(): void {
    if (Object.keys(this.albumRequest).length === 0) {
      alert('Veuillez remplir le formulaire de l\'album.');
      return;
    }
    this.http.post('/api/albums', this.albumRequest).subscribe({
      next: () => {
        this.loadAlbums();
        this.albumRequest = {};
      },
      error: (err) => console.error('Erreur création album', err)
    });
  }

  loadAlbums(): void {
    this.http.get<any[]>('/api/albums').subscribe({
      next: (data) => this.albums = data,
      error: (err) => console.error('Erreur chargement albums', err)
    });
  }

  // Discographie Methods
  onDiscographieSubmit(): void {
    if (Object.keys(this.discographieRequest).length === 0) {
      alert('Veuillez remplir le formulaire de la discographie.');
      return;
    }
    this.http.post('/api/discographies', this.discographieRequest).subscribe({
      next: () => {
        this.loadDiscographies();
        this.discographieRequest = {};
      },
      error: (err) => console.error('Erreur création discographie', err)
    });
  }

  loadDiscographies(): void {
    this.http.get<any[]>('/api/discographies').subscribe({
      next: (data) => this.discographies = data,
      error: (err) => console.error('Erreur chargement discographies', err)
    });
  }

  // Playlist Methods
  onPlaylistSubmit(): void {
    if (Object.keys(this.playlistRequest).length === 0) {
      alert('Veuillez remplir le formulaire de la playlist.');
      return;
    }
    this.http.post('/api/playlists', this.playlistRequest).subscribe({
      next: () => {
        this.loadPlaylists();
        this.playlistRequest = {};
      },
      error: (err) => console.error('Erreur création playlist', err)
    });
  }

  loadPlaylists(): void {
    this.http.get<any[]>('/api/playlists').subscribe({
      next: (data) => this.playlists = data,
      error: (err) => console.error('Erreur chargement playlists', err)
    });
  }

  // Track Methods
  onTrackSubmit(): void {
    if (Object.keys(this.trackRequest).length === 0) {
      alert('Veuillez remplir le formulaire du track.');
      return;
    }
    this.http.post('/api/tracks', this.trackRequest).subscribe({
      next: () => {
        this.loadTracks();
        this.trackRequest = {};
      },
      error: (err) => console.error('Erreur création track', err)
    });
  }

  loadTracks(): void {
    this.http.get<any[]>('/api/tracks').subscribe({
      next: (data) => this.tracks = data,
      error: (err) => console.error('Erreur chargement tracks', err)
    });
  }
}
