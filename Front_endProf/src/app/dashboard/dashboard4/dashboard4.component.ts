import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard4',
  templateUrl: './dashboard4.component.html',
  styleUrls: ['./dashboard4.component.scss'],
  template: `
    <!-- Votre template ici -->
    <select [(ngModel)]="statusFilter">
      <!-- options -->
    </select>
  `,
  styles: []
})
export class Dashboard4Component implements OnInit {
  // Ajoutez ces méthodes dans la classe ParticipationManagementComponent

// Obtient la liste des événements uniques pour les filtres
getUniqueEvents(): {id: number, name: string}[] {
  const eventsMap = new Map<number, string>();
  this.participationRequests.forEach(req => {
    eventsMap.set(req.eventId, req.eventName);
  });
  return Array.from(eventsMap.entries()).map(([id, name]) => ({id, name}));
}

// Traduit le statut pour l'affichage
getStatusLabel(status: string): string {
  const labels: {[key: string]: string} = {
    'pending': 'En attente',
    'approved': 'Approuvée',
    'rejected': 'Rejetée'
  };
  return labels[status] || status;
}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  // Données statiques de démonstration
  participationRequests = [
    {
      id: 1,
      eventId: 101,
      eventName: "Your Ultimate Party",
      userId: 1001,
      userName: "Kaso",
      userEmail: "abedLekrimBouzaine@gmail.com",
      status: "pending",
      requestDate: new Date('2023-05-15'),
      message: "Je souhaite participer à ce festival pour publier mes nouveaux tracks."
    },
    {
      id: 2,
      eventId: 101,
      eventName: "crazy Nights",
      userId: 1002,
      userName: "Master Sina",
      userEmail: "SamaraRiahi@gmail.com",
      status: "approved",
      requestDate: new Date('2023-05-10'),
      responseDate: new Date('2023-05-12'),
      message: ""
    },
    {
      id: 3,
      eventId: 102,
      eventName: "Chikha",
      userId: 1003,
      userName: "A.L.A",
      userEmail: "@example.com",
      status: "rejected",
      requestDate: new Date('2023-05-05'),
      responseDate: new Date('2023-05-08'),
      message: "."
    }
  ];

  filteredRequests = [...this.participationRequests];
  statusFilter: string = 'all';
  newRequestMessage: string = '';
  showRequestForm: boolean = false;
  selectedEventId: number = 101;

  // Filtre les demandes selon le statut sélectionné
  applyFilter(): void {
    if (this.statusFilter === 'all') {
      this.filteredRequests = [...this.participationRequests];
    } else {
      this.filteredRequests = this.participationRequests.filter(req => req.status === this.statusFilter);
    }
  }

  // Change le statut d'une demande (simulation)
  updateRequestStatus(requestId: number, newStatus: 'approved' | 'rejected'): void {
    const request = this.participationRequests.find(req => req.id === requestId);
    if (request) {
      request.status = newStatus;
      request.responseDate = new Date();
      this.applyFilter();
    }
  }

  // Supprime une demande (simulation)
  deleteRequest(requestId: number): void {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette demande ?')) {
      this.participationRequests = this.participationRequests.filter(req => req.id !== requestId);
      this.applyFilter();
    }
  }

  // Soumet une nouvelle demande (simulation)
  submitRequest(): void {
    if (this.newRequestMessage.trim()) {
      const newRequest = {
        id: this.participationRequests.length + 1,
        eventId: this.selectedEventId,
        eventName: this.participationRequests.find(e => e.eventId === this.selectedEventId)?.eventName || 'Événement',
        userId: 1004,
        userName: "Nouvel Utilisateur",
        userEmail: "nouveau@example.com",
        status: "pending",
        requestDate: new Date(),
        message: this.newRequestMessage
      };

      this.participationRequests.unshift(newRequest);
      this.newRequestMessage = '';
      this.showRequestForm = false;
      this.applyFilter();
    }
  }

  

}
