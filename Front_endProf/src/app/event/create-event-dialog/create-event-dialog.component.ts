import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Event } from 'app/models/Event';

@Component({
  selector: 'app-create-event-dialog',
  templateUrl: './create-event-dialog.component.html',
  styleUrls: ['./create-event-dialog.component.scss']
})
export class CreateEventDialogComponent {
  event: Partial<Event> = {
    title: '',
    description: '',
    date: '',
    location: '',
    validated: false,
    categoryPrices: {},
    totalTicketsByCategory: {},
    remainingTicketsByCategory: {},
    imageUrl: '' // Ajout du champ pour l'URL de l'image
  };

  categories: Array<{
    name: string;
    price: number;
    quantity: number;
  }> = [
    { name: 'Standard', price: 0, quantity: 1 }
  ];

  imageFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  isDragOver = false;

  constructor(public activeModal: NgbActiveModal) {}

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    this.handleImageFile(file);
  }

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = true;
  }

  onDragLeave(): void {
    this.isDragOver = false;
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    this.isDragOver = false;
    
    if (event.dataTransfer?.files) {
      const file = event.dataTransfer.files[0];
      this.handleImageFile(file);
    }
  }

  handleImageFile(file: File): void {
    if (file && file.type.match('image.*')) {
      this.imageFile = file;

      // Créer un aperçu de l'image
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(): void {
    this.imageFile = null;
    this.imagePreview = null;
  }

  addCategory(): void {
    this.categories.push({ name: '', price: 0, quantity: 1 });
  }

  removeCategory(index: number): void {
    this.categories.splice(index, 1);
  }

  onSubmit(): void {
    // Convert categories to event format
    this.event.categoryPrices = {};
    this.event.totalTicketsByCategory = {};
    this.event.remainingTicketsByCategory = {};

    this.categories.forEach(category => {
      this.event.categoryPrices[category.name] = category.price;
      this.event.totalTicketsByCategory[category.name] = category.quantity;
      this.event.remainingTicketsByCategory[category.name] = category.quantity;
    });

    // Créer un objet FormData pour envoyer l'image avec les autres données
    const formData = new FormData();
    formData.append('event', JSON.stringify(this.event));
    if (this.imageFile) {
      formData.append('image', this.imageFile);
    }

    this.activeModal.close(formData);
  }
}