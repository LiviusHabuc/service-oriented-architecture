import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {WebSocketService} from "./web-socket.service";

@Injectable({
  providedIn: 'root'
})
export class DisplayCardsService {

  constructor(private http: HttpClient, private webSocketService: WebSocketService) {
  }

  getAllDisplayCards(): Observable<any> {
    const url = "http://localhost/api/cards/all-cards";
    const headers = this.getAuthHeader("user", "password");
    const options = {
      headers: headers,
    };
    return this.http.get(url, options);
  }

  addDisplayCard(card: { message: string }): Observable<any> {
    const url = "http://localhost:8082/api/msg/send";
    const headers = this.getAuthHeader("user", "password");
    const options = {
      headers: headers,
    };
    return this.http.post(url, card, options);
  }

  private getAuthHeader(username: string, password: string): HttpHeaders {
    const credentials = btoa(`${username}:${password}`);
    return new HttpHeaders({
      "Authorization": `Basic ${credentials}`,
      "Content-Type": "application/json",
    });
  }
}
