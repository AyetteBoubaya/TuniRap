import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Message {
  id: number;
  senderId: number;
  collaborationId: number;
  content: string;
  timestamp: string;
}

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/api/messages';

  constructor(private http: HttpClient) { }

  sendMessage(collaborationId: number, senderId: number, content: string): Observable<Message> {
    const params = new HttpParams()
      .set('collaborationId', collaborationId)
      .set('senderId', senderId)
      .set('content', content);
    
    return this.http.post<Message>(`${this.apiUrl}/send`, null, { params });
  }

  getMessagesByCollaboration(collaborationId: number): Observable<Message[]> {
    return this.http.get<Message[]>(`${this.apiUrl}/by-collaboration/${collaborationId}`);
  }

  getRecentMessages(collaborationId: number, limit: number): Observable<Message[]> {
    const params = new HttpParams().set('limit', limit.toString());
    return this.http.get<Message[]>(`${this.apiUrl}/recent/${collaborationId}`, { params });
  }
}
