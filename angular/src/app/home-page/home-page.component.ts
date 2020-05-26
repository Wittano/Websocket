import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import * as jwtToken from 'jwt-decode';
import { JwtToken } from '../models/jwt-token';
import { Message } from '../models/message';
import {Client, Message as StompMessage, client as StompClient} from "stompjs";


@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements AfterViewInit {
  private stompClient: Client;

  username: string;
  message: Message;

  constructor(private router: Router, private userService: UserService) {
    const token: string = localStorage.getItem('token');
    const decodeToken: JwtToken = jwtToken<JwtToken>(token);

    this.message = new Message('root', 'bob', 'Hello Bob', new Date());

    this.stompClient = StompClient("ws://localhost:8080/message");
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe("/chat/test", (msg: StompMessage) => {
        console.log(msg.body);
        this.message = JSON.parse(msg.body);
      });
    });

    this.username = decodeToken.sub;
  }

  ngAfterViewInit() {
    this.tokenExpired();
  }

  private static isSessionEnd(token: string | null): boolean{
    const expired = token != null ? new Date(jwtToken<JwtToken>(token).exp) : null;
    return token != null && expired != null && HomePageComponent.afterDate(new Date(), expired);
  }

  private static afterDate(date: Date, afterDate: Date): boolean{
    return afterDate > date;
  }

  private tokenExpired(): void{
    const token: string | null = localStorage.getItem('token');
    if (HomePageComponent.isSessionEnd(token)) {
      this.logout();
    }
  }

  logout() {
    this.userService.logout();
  }

  sendMessage() {
    this.tokenExpired();
    const msg = JSON.stringify({
      from: 'root',
      to: 'bober',
      content: 'Hi Mark!'
    });
    fetch('http://localhost:8080/send', {
      method: 'POST',
      body: msg,
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:8080'
      },
    }).catch((err: Error) => {
      console.log(err.message);
    });
  }
}
