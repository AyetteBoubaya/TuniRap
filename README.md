# 🎤 TuniRap

> Ma **première application en architecture microservices** : une plateforme dédiée à la scène rap tunisienne (catalogue musical, collaborations entre artistes, billetterie d'événements, notifications).

Ce dépôt documente mon parcours d'apprentissage des microservices avec **Spring Boot / Spring Cloud**, **Kafka**, **MongoDB**, **MySQL**, **Elasticsearch** et **Angular**. Il n'est pas nécessaire de le lancer pour le consulter : ce README présente ce que j'ai construit, comment c'est organisé et ce que j'ai appris.

---

## 🎯 L'idée

Les artistes rap tunisiens sont dispersés entre plusieurs plateformes. TuniRap centralise au même endroit :

- 🎵 **leur catalogue** (albums, singles, playlists), importé automatiquement depuis Spotify
- 🤝 **la mise en relation** entre artistes pour des collaborations, avec chat en direct
- 🎟️ **la billetterie** de leurs événements
- 🔔 **les notifications** en temps réel

---

## 🏗 Architecture

```
                        ┌──────────────────────┐
                        │  Front-end Angular   │
                        └──────────┬───────────┘
                                   │
                        ┌──────────▼───────────┐        ┌────────────────┐
                        │     API Gateway      │◄──────►│  Eureka Server │
                        │  (Spring Cloud GW)   │        │   (discovery)  │
                        └──────────┬───────────┘        └────────────────┘
        ┌─────────────┬────────────┼─────────────┬──────────────┐
        ▼             ▼            ▼             ▼              ▼
  ┌──────────┐ ┌────────────┐ ┌────────────┐ ┌──────────┐ ┌───────────────┐
  │   auth   │ │ catalogue  │ │collaboration│ │  events  │ │ notifications │
  └────┬─────┘ └─────┬──────┘ └─────┬──────┘ └────┬─────┘ └───────┬───────┘
    MySQL        MongoDB +        MySQL         MongoDB          MongoDB
                 Elasticsearch
                       └──────────── Kafka ────────────┘

  Script Python (API Spotify) ──► Kafka ──► catalogue
```

### Les 7 services

| Service | Rôle | Techno clé |
|---|---|---|
| **EEureka** | Annuaire des services (service discovery) | Netflix Eureka |
| **ApiGetway** | Point d'entrée unique, routage, CORS | Spring Cloud Gateway |
| **authservice** | Inscription, connexion JWT, refresh token, vérification e-mail, OTP de réinitialisation, rôles (`ARTIST`, `ORGANISATEUR`, `USER`, `ADMIN`) | Spring Security, MySQL |
| **catalogueManagement** | CRUD discographies / albums / tracks / playlists, recherche plein texte, import Spotify | MongoDB, Elasticsearch, Kafka, OpenFeign |
| **collaborationManagement** | Demandes de collaboration (envoi, acceptation) et chat temps réel | WebSocket / STOMP, MySQL, Kafka |
| **ServicesEventMangement** | Événements, billets, panier, validation / annulation | MongoDB |
| **notifications** | Notifications persistées et poussées en temps réel | Kafka consumer, WebSocket, MongoDB |

### Communication entre services

| Type | Exemple |
|---|---|
| **Synchrone (REST / Feign)** | `catalogue` interroge `auth` pour récupérer les informations d'un utilisateur |
| **Asynchrone (Kafka)** | `collaboration` publie `collaboration-requests` et `collaboration-accepted`, `notifications` les consomme |
| **Pipeline de données** | Un script Python récupère les données Spotify et les publie sur `catalogue-events`, consommé par `catalogue` |
| **Temps réel** | WebSocket / STOMP pour le chat et les notifications |

---

## ✨ Ce que j'ai mis en pratique

- **Découpage en services** par domaine métier, avec une base de données adaptée à chacun (*polyglot persistence* : MySQL, MongoDB, Elasticsearch)
- **Service discovery et gateway** avec Eureka et Spring Cloud Gateway
- **Architecture événementielle** avec Kafka (producteurs, consommateurs, sérialisation JSON, gestion des erreurs de désérialisation)
- **Authentification JWT** complète : access token, refresh token, vérification d'e-mail, OTP par e-mail
- **Recherche** avec Elasticsearch (par titre, par titre et genre)
- **Temps réel** avec WebSocket / STOMP
- **Intégration d'une API externe** (Spotify) via un script de scraping Python
- **Conteneurisation** de l'infrastructure avec Docker Compose (Kafka, Zookeeper, MongoDB, MySQL, Elasticsearch)
- **Documentation d'API** avec Swagger / OpenAPI
- **Front-end Angular** consommant les services

---

## 🧰 Stack

**Back-end** : Java 17/21 · Spring Boot 3 · Spring Cloud · Spring Security · Spring Data · Spring Kafka · WebSocket · OpenFeign · Lombok
**Données** : MySQL · MongoDB · Elasticsearch
**Messaging** : Apache Kafka · Zookeeper
**Front-end** : Angular 14 · Bootstrap · ApexCharts
**Outils** : Maven · Docker · Docker Compose · Swagger · Python (spotipy, pandas)

---

## 📸 Aperçu

*Ajoutez ici 3 ou 4 captures d'écran : tableau de bord Eureka avec les services enregistrés, interface Angular, Swagger d'un service, chat en direct.*

```
docs/
├── eureka-dashboard.png
├── swagger-catalogue.png
└── app-home.png
```

---

## 📚 Ce que j'ai appris

Ce projet m'a surtout appris ce qui **ne se voit pas** dans un tutoriel :

- **La configuration est le vrai défi.** Les ports, les URLs, les noms de services Eureka, les préfixes de routes de la Gateway : une petite incohérence suffit à casser un appel. J'ai compris l'intérêt de `StripPrefix`, des context-paths et des variables d'environnement.
- **Un service = une base.** Choisir MySQL ou MongoDB par service force à bien réfléchir au modèle de données et aux frontières de chaque domaine.
- **L'asynchrone change la façon de penser.** Avec Kafka, le producteur ne sait pas qui consomme. Il faut penser aux formats d'événements, aux packages de désérialisation et aux messages invalides.
- **La sécurité doit être pensée globalement.** Authentifier dans chaque service ou une seule fois à la Gateway ? Je vois maintenant les avantages de centraliser la validation du JWT.
- **Docker Compose pour l'infrastructure d'abord.** Lancer Kafka, MongoDB et Elasticsearch en une commande simplifie beaucoup le développement.
- **Ne jamais committer de secrets.** J'ai appris l'importance des variables d'environnement et de la révocation des clés exposées.

## 🔭 Pistes d'amélioration

Ce que je ferais dans une prochaine version :

- Valider le JWT directement dans la Gateway et propager l'identité aux services
- Conteneuriser tous les services (Dockerfile multi-étapes) et compléter le Compose
- Externaliser la configuration (Spring Cloud Config ou variables d'environnement)
- Ajouter des tests d'intégration avec Testcontainers et une CI GitHub Actions
- Intégrer un vrai paiement dans le checkout des billets
- Ajouter résilience et observabilité (Resilience4j, Micrometer, traçage distribué)

---

<details>
<summary><b>🚀 Lancer le projet en local (optionnel)</b></summary>

**Prérequis** : JDK 17+, Maven, Docker, Node.js, Python 3 (scraping uniquement).

```bash
# 1. Infrastructure
docker compose up -d elasticsearch mongodb mysql-db zookeeper kafka eureka-server

# 2. Chaque microservice (Eureka → auth → services métier → gateway)
cd authservice && ./mvnw spring-boot:run

# 3. Front-end
cd Front_endProf && npm install && npm start     # http://localhost:4200
```

Les secrets (SMTP, clé JWT, mots de passe de base, clés Spotify) se définissent par variables d'environnement, jamais dans le dépôt.

| Service | Port |
|---|---|
| Eureka | 8761 |
| API Gateway | 8099 |
| Auth | 8887 |
| Catalogue | 8089 |
| Collaboration | 8090 |
| Événements | 8081 |
| Notifications | 8082 |

**Import Spotify** :

```bash
cd catalogueManagement/scraping
pip install spotipy pandas kafka-python
export SPOTIFY_CLIENT_ID=... SPOTIFY_CLIENT_SECRET=...
python scraping.py
```

</details>

---

## 👤 Auteur

Projet réalisé à l'ESPRIT dans le cadre de mon apprentissage des architectures microservices.
*Ajoutez ici votre nom, votre lien LinkedIn et le nom de vos coéquipiers s'il y en a.*
