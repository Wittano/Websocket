import { Injectable } from '@angular/core';
import { Message } from '../models/message';
import {
  client as Client,
  Client as StompClient,
  Message as StompMessage
} from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private stompClient: StompClient;

  constructor() {
    this.stompClient = Client('ws://localhost:8080/message');
  }

  disconnect() {
    if (this.stompClient.connected) {
      this.stompClient.disconnect(() => {});
    }
  }

  subscribe(from: string, to: string, callback: (message: Message) => void) {
    this.disconnect();
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/chat/${from}-${to}`, (msg: StompMessage) => {
        callback(JSON.parse(msg.body));
      });
    });
  }

  sendMessage(message: Message): void {
    fetch('http://localhost:8080/send', {
      method: 'POST',
      body: JSON.stringify({
        from: message.from,
        to: message.to,
        content: message.content
      }),
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:8080'
      }
    }).catch((err: Error) => {
      console.log(err.message);
    });
  }
}
