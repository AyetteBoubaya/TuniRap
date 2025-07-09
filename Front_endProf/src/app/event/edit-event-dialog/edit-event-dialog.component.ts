import { Component, Input } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Event } from 'app/models/Event';

@Component({
  selector: 'app-edit-event-dialog',
  templateUrl: './edit-event-dialog.component.html'
})
export class EditEventDialogComponent {
  @Input() event: Event;

  constructor(public activeModal: NgbActiveModal) {}
}