import { Component, OnInit, ElementRef, Renderer2, OnDestroy } from '@angular/core';
import { LayoutService } from '../services/layout.service';
import { Subscription } from 'rxjs';
import { StorageService } from '../auth/storage.service';

@Component({
  selector: 'app-notification-sidebar',
  templateUrl: './notification-sidebar.component.html',
  styleUrls: ['./notification-sidebar.component.scss']
})
export class NotificationSidebarComponent implements OnInit, OnDestroy {
  layoutSub: Subscription;
  isOpen = false;
  notifications: { title: string; date: string; }[];
  ngOnInit() {}
  constructor(private layoutService: LayoutService) {
    this.layoutSub = layoutService.toggleNotiSidebar$.subscribe(
      open => {
        this.isOpen = open;
      });
  }
  ngOnDestroy() {
    if (this.layoutSub) {
      this.layoutSub.unsubscribe();
    }
  }
  onClose() {
    this.layoutService.toggleNotificationSidebar(false);
  }

  loadNotifications(): void {
  const role = StorageService.getUserRole();

  if (role === 'ADMIN') {
    this.notifications = [
      { title: 'Nouvelle demande d\'artiste', date: '14/06/2025' },
      { title: 'Statistiques hebdomadaires disponibles', date: '13/06/2025' }
    ];
  } else if (role === 'FAN') {
    this.notifications = [
      { title: 'Nouveau concert ajouté', date: '14/06/2025' },
      { title: 'Votre billet a été confirmé', date: '13/06/2025' }
    ];
  } else if (role === 'ARTIST') {
    this.notifications = [
      { title: 'Demande de collaboration reçue', date: '14/06/2025' },
      { title: 'Votre événement a été approuvé', date: '13/06/2025' }
    ];
  } else if (role === 'ORGANIZATEUR') {
    this.notifications = [
      { title: 'Nouvelle demande de réservation', date: '14/06/2025' },
      { title: 'Artiste ajouté à votre événement', date: '13/06/2025' }
    ];
  } else {
    // Aucun rôle ou rôle inconnu
    this.notifications = [];
  }
}

}
