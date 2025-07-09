import { NgModule } from '@angular/core';
import { CommonModule } from "@angular/common";
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { QuillModule } from 'ngx-quill';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { PipeModule } from 'app/shared/pipes/pipe.module';
import { DatePipe } from '@angular/common';
import { EventComponent } from './event.component';
import { EventRoutingModule } from './event-routing.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { CreateEventDialogComponent } from './create-event-dialog/create-event-dialog.component';
import { EditEventDialogComponent } from './edit-event-dialog/edit-event-dialog.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        NgbModule,
        QuillModule.forRoot(),
        PerfectScrollbarModule,
        PipeModule,
        EventRoutingModule,
        NgxPaginationModule
    ],
    declarations: [
        EventComponent,
        CreateEventDialogComponent,
        EditEventDialogComponent
    ],
    providers: [DatePipe],
})
export class EventModule { }