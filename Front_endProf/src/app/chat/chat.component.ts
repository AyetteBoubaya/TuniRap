import { Component, ViewChild, ElementRef, OnInit, ChangeDetectionStrategy, Renderer2, Inject, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import { ConfigService } from 'app/shared/services/config.service';
import { Subscription } from 'rxjs';
import { Message, MessageService } from 'app/services/message.service';
import { WebSocketChatService } from './websocker-chat.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit, OnDestroy {

  messages: Message[] = [];
  collaborationId = 1;  // <-- à adapter selon contexte
  senderId = 2;         // <-- à adapter selon l'utilisateur connecté
  newMessage = "";
  searchQuery: string = '';
  placement = "bottom-right";
  isContentOverlay = false;

  public config: any = {};
  layoutSub: Subscription;

  constructor(
    private elRef: ElementRef,
    private renderer: Renderer2,
    @Inject(DOCUMENT) private document: Document,
    private configService: ConfigService,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService,
    private wsService: WebSocketChatService
  ) {
    this.config = this.configService.templateConf;
    this.renderer.addClass(this.document.body, "chat-application");
  }

  ngOnInit() {
    this.loadMessages();
  }

  ngOnDestroy() {
    if (this.layoutSub) {
      this.layoutSub.unsubscribe();
    }
    this.renderer.removeClass(this.document.body, "chat-application");
  }

  loadMessages() {
    this.messageService.getMessagesByCollaboration(this.collaborationId).subscribe(data => {
      this.messages = data;
      this.cdr.markForCheck();
    });
  }

  onAddMessage() {
    if (this.newMessage.trim() !== "") {
      this.messageService.sendMessage(this.collaborationId, this.senderId, this.newMessage)
        .subscribe(sentMessage => {
          this.messages.push(sentMessage);
          this.newMessage = "";
          this.cdr.markForCheck();
        });
    }
  }

  onSidebarToggle() {
    this.isContentOverlay = true;
  }

  onContentOverlay() {
    this.isContentOverlay = false;
  }

}
