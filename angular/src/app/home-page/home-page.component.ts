import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit
} from '@angular/core';
import { UserService } from '../service/user.service';
import * as jwtToken from 'jwt-decode';
import { JwtToken } from '../models/jwt-token';
import { Message } from '../models/message';
import { MessageService } from '../service/message-service.service';
import { FormControl, Validators } from '@angular/forms';
import { FriendService } from '../service/friend.service';
import { ErrorService } from '../service/error.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomePageComponent implements AfterViewInit, OnDestroy, OnInit {
  username: string;
  messageInput: FormControl;
  selectedFriend: string;
  messageList: Array<Message>;
  friendList: Array<string>;
  searchFriendInput: FormControl;
  disable: boolean;
  errorMessage: string;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private changeDetection: ChangeDetectorRef,
    private friendService: FriendService,
    private errorService: ErrorService
  ) {
    const token: string = localStorage.getItem('token');
    const decodeToken: JwtToken = jwtToken<JwtToken>(token);

    this.messageList = new Array<Message>();
    this.friendList = new Array<string>();
    this.username = decodeToken.sub;
    this.messageInput = new FormControl(
      {
        value: '',
        disabled: false
      },
      Validators.minLength(1)
    );
    this.searchFriendInput = new FormControl('', [
      Validators.minLength(4),
      Validators.required
    ]);
    this.disable = true;
    this.errorService.errorMessage.subscribe((value: string) => {
      this.errorMessage = value;
    });
  }

  ngOnInit(): void {
    this.tokenExpired();
    this.friendService
      .getFriends(this.username)
      .then((result: string[]) => {
        this.friendList = result;
        this.changeDetection.detectChanges();
      })
      .catch(() => {
        this.friendList.push('bob');
        this.changeDetection.detectChanges();
      });
  }

  ngAfterViewInit() {
    this.tokenExpired();
    this.messageService.subscribe(
      `${this.username}-notification`,
      (message: string) => {
        if (!this.friendList.includes(message)) {
          this.friendList.push(message);
          this.changeDetection.detectChanges();
          this.friendService.addFriend(this.username, message);
        }
      }
    );
  }

  ngOnDestroy() {
    this.messageService.disconnect();
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
      new Date(),
      this.createQueueName()
    );

    this.messageService.sendMessage(message);
    this.messageInput.setValue('');
  }

  async selectFriend($event) {
    this.selectedFriend = $event.target.innerHTML.trim();
    this.subscribe();
    this.messageList = await this.messageService.getCorrespondence(
      this.username,
      this.selectedFriend
    );
    this.changeDetection.detectChanges();
  }

  setSearchFriend() {
    this.disable = !this.searchFriendInput.valid;
  }

  addFriend() {
    this.tokenExpired();
    this.friendService.addFriend(this.username, this.searchFriendInput.value);
    if (this.errorMessage === '') {
      this.friendList.push(this.searchFriendInput.value);
      this.changeDetection.detectChanges();
      this.clearSearchFriendInput();
    }
  }

  removeFriend() {
    this.tokenExpired();
    this.friendService.removeFriend(
      this.username,
      this.searchFriendInput.value
    );
    if (this.errorMessage === '') {
      this.friendList = this.friendList.filter(
        (value: string) => value !== this.searchFriendInput.value
      );
      this.changeDetection.detectChanges();
      this.clearSearchFriendInput();
    }
  }

  private clearSearchFriendInput() {
    this.disable = true;
    this.selectedFriend = '';
    this.searchFriendInput.setValue('');
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
    this.messageService.subscribeOneQueue(
      this.createQueueName(),
      (msg: Message) => {
        this.messageList.push(msg);
        this.changeDetection.detectChanges();
      }
    );
  }

  private createQueueName(): string {
    return [this.username, this.selectedFriend]
      .sort()
      .toString()
      .replace(',', '-');
  }
}
