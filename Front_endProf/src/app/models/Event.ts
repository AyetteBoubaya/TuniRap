import { CategoryEnum } from './CategoryEnum';
import { Centre } from './Centre';
import { MainCategory } from './MainCategory';
import { User } from './User';

export class Event {
  id: string;
  title: string;
  description: string;
  date: Date | string;
  location: string;
  validated: boolean;
  categoryPrices: { [key: string]: number };
  totalTicketsByCategory: { [key: string]: number };
  remainingTicketsByCategory: { [key: string]: number };
  imageUrl:{};
  // Add other properties as needed
}
