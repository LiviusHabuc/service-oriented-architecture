import {Injectable} from '@angular/core';
import {Client} from '@stomp/stompjs';
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  private client: Client;
  private displayCardsSubject: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
  private messageSubject: BehaviorSubject<string> = new BehaviorSubject<string>("");

  constructor() {
    this.client = new Client({
      brokerURL: "ws://localhost:8083/ws",
      reconnectDelay: 5000,
    });

    this.client.onConnect = () => {
      this.client.subscribe("/topic/messages", (message) => {
        if (message.body) {
          this.messageSubject.next(message.body);
          console.log("Received:", message.body);
        }
      });
    };

    this.client.activate();

    this.connectDisplayCardsWebSocket();
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

  getDisplayCardsUpdates(): Observable<any[]> {
    return this.displayCardsSubject.asObservable();
  }

  getKafkaMessageUpdate(): Observable<string> {
    return this.messageSubject.asObservable();
  }
}
