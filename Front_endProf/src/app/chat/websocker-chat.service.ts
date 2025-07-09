import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { Client, Message } from '@stomp/stompjs';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class WebSocketChatService {
  private client: Client;
  private messageSubject = new Subject<any>();

  messageObservable = this.messageSubject.asObservable();

  connect(collabId: string) {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws')
    });

    this.client.onConnect = () => {
      this.client.subscribe('/topic/collaboration-' + collabId, (message: Message) => {
        this.messageSubject.next(JSON.parse(message.body));
      });
    };

    this.client.activate();
  }

  sendMessage(destination: string, message: any) {
    this.client.publish({
      destination: destination,
      body: JSON.stringify(message)
    });
  }
}
