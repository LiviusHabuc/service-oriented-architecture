import { Component } from '@angular/core';
import {DisplayCardsService} from "../service/display-cards.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-display-cards',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './display-cards.component.html',
  styleUrl: './display-cards.component.css'
})
export class DisplayCardsComponent {

  displayCardsList: any[] = [];

  constructor(private displayCardsService: DisplayCardsService) {
  }

  ngOnInit() {
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
}
