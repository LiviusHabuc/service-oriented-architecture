import {Injectable} from '@angular/core';
import {Client} from '@stomp/stompjs';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  public messages: string[] = [];
  private client: Client;
  private displayCardsSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);

  constructor() {
    this.client = new Client({
      brokerURL: "ws://localhost:8083/ws",
      reconnectDelay: 5000,
    });

    this.client.onConnect = () => {
      this.client.subscribe("/topic/messages", (message) => {
        if (message.body) {
          this.messages.push(message.body);
          console.log("Received:", message.body);
        }
      });
    };

    this.client.activate();

    this.connectDisplayCardsWebSocket();
  }

  getDisplayCardsUpdates(): Observable<any[]> {
    return this.displayCardsSubject.asObservable();
  }

  private connectDisplayCardsWebSocket() {
    const displayCardsClient = new Client({
      brokerURL: "ws://localhost:8081/ws",
      reconnectDelay: 5000,
    });

    displayCardsClient.onConnect = () => {
      displayCardsClient.subscribe("/topic/display-cards", (message) => {
        if (message.body) {
          const updatedCards = JSON.parse(message.body);
          this.displayCardsSubject.next(updatedCards);
        }
      });
    };

    displayCardsClient.activate();
  }
}
