import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Event {
  id: string;
  title: string;
  date: string;
  location: string;
  validated: boolean;
  totalTicketsByCategory: { [key: string]: number };
  remainingTicketsByCategory: { [key: string]: number };
}

@Injectable({
  providedIn: 'root'
})
export class EventService {
  private apiUrl = 'http://localhost:8085/api/events/event';  // adapte l’URL selon ton API

  constructor(private http: HttpClient) {}

  getAllEvents(): Observable<Event[]> {
    return this.http.get<Event[]>(this.apiUrl);
  }
  deleteEvent(eventId: number): Observable<void> {
    return this.http.delete<void>(`/api/events/${eventId}`);
  }

}
