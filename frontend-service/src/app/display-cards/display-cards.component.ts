import {Component} from '@angular/core';
import {DisplayCardsService} from "../service/display-cards.service";
import {NgForOf} from "@angular/common";
import {WebSocketService} from "../service/web-socket.service";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-display-cards',
  standalone: true,
  imports: [
    NgForOf,
    FormsModule
  ],
  templateUrl: './display-cards.component.html',
  styleUrl: './display-cards.component.css'
})
export class DisplayCardsComponent {

  displayCardsList: any[] = [];
  newCardMessage: string = "";

  constructor(
    private displayCardsService: DisplayCardsService,
    private webSocketService: WebSocketService
  ) {
  }

  ngOnInit() {
    this.fetchCards();

    this.webSocketService.getDisplayCardsUpdates().subscribe({
      next: updatedList => {
        this.displayCardsList = updatedList;
      },
      error: error => {
        console.log("Display cards list websocket error:", error);
      }
    });
  }

  fetchCards() {
    this.displayCardsService.getAllDisplayCards().subscribe(
      {
        next: data => {
          this.displayCardsList = data;
        },
        error: error => {
          console.log(error);
        }
      }
    );
  }

  addDisplayCard() {
    if (!this.newCardMessage.trim())
      return;

    this.displayCardsService.addDisplayCard({message: this.newCardMessage}).subscribe({
      next: () => {
        this.newCardMessage = "";
      },
      error: error => {
        console.log("Error adding card:", error);
      }
    });
  }
}
