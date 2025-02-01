import { Routes } from '@angular/router';
import {DisplayCardsComponent} from "./display-cards/display-cards.component";

export const routes: Routes = [
  {
    path: "",
    redirectTo: "display-cards",
    pathMatch: "full"
  },
  {
    path: "display-cards",
    component: DisplayCardsComponent
  }
];
