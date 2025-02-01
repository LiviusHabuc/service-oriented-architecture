import {Injectable} from '@angular/core';
import {Client} from '@stomp/stompjs';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  public messages: string[] = [];
  private client: Client;

  constructor() {
    this.client = new Client({
      brokerURL: "ws://localhost:8083/ws",
      reconnectDelay: 5000, // Auto-reconnect every 5 seconds
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
  }
}
