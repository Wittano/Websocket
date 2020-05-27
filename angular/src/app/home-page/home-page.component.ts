import { Component, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';
import * as jwtToken from 'jwt-decode';
import { JwtToken } from '../models/jwt-token';
import { Message } from '../models/message';
import { MessageService } from '../service/message-service.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements AfterViewInit {
  username: string;
  message: Message;
  messageInput: FormControl;

  constructor(
    private userService: UserService,
    private messageService: MessageService
  ) {
    const token: string = localStorage.getItem('token');
    const decodeToken: JwtToken = jwtToken<JwtToken>(token);

    this.message = new Message('root', 'bob', 'Hello Bob', new Date());

    messageService.subscribe('root', 'bob', (msg: Message) => {
      console.log(msg);
    });

    this.username = decodeToken.sub;
  }

  ngAfterViewInit() {
    this.tokenExpired();
  }

  private isSessionEnd(token: string | null): boolean {
    const expired =
      token != null ? new Date(jwtToken<JwtToken>(token).exp) : null;

    return (
      token != null && expired != null && this.afterDate(new Date(), expired)
    );
  }

  private afterDate(date: Date, afterDate: Date): boolean {
    return afterDate > date;
  }

  private tokenExpired(): void {
    const token: string | null = localStorage.getItem('token');
    if (this.isSessionEnd(token)) {
      this.logout();
    }
  }

  logout() {
    this.userService.logout();
  }

  sendMessage() {
    this.tokenExpired();

    const message: Message = new Message(
      this.username,
      'root',
      null,
      new Date()
    );

    this.messageService.sendMessage(message);
  }
}
