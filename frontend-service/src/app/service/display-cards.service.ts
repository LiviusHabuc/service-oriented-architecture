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

  private getAuthHeader(username: string, password: string): HttpHeaders {
    const credentials = btoa(`${username}:${password}`);
    return new HttpHeaders({
      'Authorization': `Basic ${credentials}`,
      'Content-Type': 'application/json',
    });
  }

  getAllDisplayCards(): Observable<any> {
    const url = 'http://localhost:8081/api/cards/all-cards';
    const headers = this.getAuthHeader("user", "password");
    const options = {
      headers: headers,
    };
    return this.http.get(url, options);
  }
}
