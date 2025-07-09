import { Component, OnInit } from '@angular/core';
import { EventService } from 'app/services/event.service';
import { Event } from 'app/models/Event';
import { DatePipe } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CreateEventDialogComponent } from './create-event-dialog/create-event-dialog.component';
import { EditEventDialogComponent } from './edit-event-dialog/edit-event-dialog.component';
import { ConfirmDialogComponent } from 'app/shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.scss'],
  providers: [DatePipe]
})
export class EventComponent implements OnInit {

  events: Event[] = [];
  filteredEvents: Event[] = [];

  // Filtres
  searchTerm: string = '';
  statusFilter: string = 'all';
  sortBy: string = 'date';

  isLoading: boolean = false;

  constructor(
    private eventService: EventService,
    private modalService: NgbModal,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.isLoading = true;
    this.eventService.getAllEvents().subscribe({
      next: (events) => {
        this.events = events;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des événements :', err);
        this.isLoading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredEvents = this.events.filter(event => {
      const matchesSearch = !this.searchTerm || event.title.toLowerCase().includes(this.searchTerm.toLowerCase());

      const isAvailable = Object.values(event.remainingTicketsByCategory).some(value => value > 0);
      const matchesStatus = this.statusFilter === 'all' ||
        (this.statusFilter === 'available' && isAvailable) ||
        (this.statusFilter === 'soldOut' && !isAvailable);

      return matchesSearch && matchesStatus;
    });

    if (this.sortBy === 'date') {
      this.filteredEvents.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
    }
  }

  openCreateEventDialog(): void {
    const modalRef = this.modalService.open(CreateEventDialogComponent, { size: 'lg' });
    modalRef.result.then((result) => {
      if (result) {
        this.loadEvents();
      }
    }, () => {});
  }

  openEditDialog(event: Event): void {
    const modalRef = this.modalService.open(EditEventDialogComponent, { size: 'lg' });
    modalRef.componentInstance.event = event;
    modalRef.result.then((result) => {
      if (result) {
        this.loadEvents();
      }
    }, () => {});
  }

  deleteEvent(eventId: number): void {
    const modalRef = this.modalService.open(ConfirmDialogComponent);
    modalRef.componentInstance.title = 'Supprimer l\'événement';
    modalRef.componentInstance.message = 'Êtes-vous sûr de vouloir supprimer cet événement ?';
    modalRef.result.then((confirmed) => {
      if (confirmed) {
        this.eventService.deleteEvent(eventId).subscribe({
          next: () => this.loadEvents(),
          error: (err) => console.error('Erreur lors de la suppression de l\'événement :', err)
        });
      }
    }, () => {});
  }

  getEventCategories(event: Event) {
    return Object.keys(event.categoryPrices);
  }

  getRemainingTickets(event: Event, category: string) {
    return event.remainingTicketsByCategory[category];
  }

  getTotalTickets(event: Event, category: string) {
    return event.totalTicketsByCategory[category];
  }

  getCategoryPrice(event: Event, category: string) {
    return event.categoryPrices[category];
  }

  isEventSoldOut(eventId: number): boolean {
    const event = this.events.find(e => e.id === eventId);
    if (!event) return false;
    return Object.values(event.remainingTicketsByCategory).every(value => value === 0);
  }
}
