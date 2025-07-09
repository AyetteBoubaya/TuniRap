import { Component, OnInit } from '@angular/core';
import { Message, MessageService } from 'app/services/message.service';

@Component({
  selector: 'app-collab-chat',
  templateUrl: './collab-chat.component.html'
})
export class CollabChatComponent implements OnInit {

  messages: Message[] = [];
  collaborationId = 1; // à récupérer dynamiquement selon ton contexte
  senderId = 2;
  newMessageContent = '';

  constructor(private messageService: MessageService) { }

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    this.messageService.getMessagesByCollaboration(this.collaborationId).subscribe(data => {
      this.messages = data;
    });
  }

  sendMessage() {
    if (this.newMessageContent.trim() !== '') {
      this.messageService.sendMessage(this.collaborationId, this.senderId, this.newMessageContent)
        .subscribe(message => {
          this.messages.push(message);
          this.newMessageContent = '';
        });
    }
  }
}
