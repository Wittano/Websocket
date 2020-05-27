import {
  Component,
  AfterViewInit,
  OnDestroy,
  ChangeDetectionStrategy,
  ChangeDetectorRef
} from '@angular/core';
import { UserService } from '../service/user.service';
import * as jwtToken from 'jwt-decode';
import { JwtToken } from '../models/jwt-token';
import { Message } from '../models/message';
import { MessageService } from '../service/message-service.service';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomePageComponent implements AfterViewInit, OnDestroy {
  username: string;
  messageInput: FormControl;
  selectedFriend: string;
  messageList: Array<Message>;
  friendList: Array<string>;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private changeDetection: ChangeDetectorRef
  ) {
    const token: string = localStorage.getItem('token');
    const decodeToken: JwtToken = jwtToken<JwtToken>(token);

    this.messageList = new Array<Message>();
    this.friendList = new Array<string>('bob');
    this.username = decodeToken.sub;
    this.messageInput = new FormControl('');
  }

  ngAfterViewInit() {
    this.tokenExpired();
  }

  ngOnDestroy() {
    this.messageService.disconnect();
  }

  private isSessionEnd(token: string | null): boolean {
    const expired: Date | null =
      token != null ? new Date(jwtToken<JwtToken>(token).exp) : null;

    return token != null && expired != null && expired > new Date();
  }

  private tokenExpired(): void {
    const token: string | null = localStorage.getItem('token');
    if (this.isSessionEnd(token)) {
      this.logout();
    }
  }

  private subscribe(): void {
    this.messageService.subscribe(
      this.username,
      this.selectedFriend,
      (msg: Message) => {
        this.messageList.push(msg);
        this.changeDetection.detectChanges();
      }
    );
  }

  logout() {
    this.userService.logout();
  }

  sendMessage() {
    this.tokenExpired();

    const message: Message = new Message(
      this.username,
      this.selectedFriend,
      this.messageInput.value,
      new Date()
    );

    this.messageService.sendMessage(message);
  }

  async selectFriend($event) {
    this.selectedFriend = $event.target.innerHTML.trim();
    this.subscribe();
    this.messageList = await this.messageService.getCorrespondence(this.username, this.selectedFriend);
    this.changeDetection.detectChanges();
  }
}
