import {Injectable} from '@angular/core';
import {Message} from '../models/message';
import {client as Client, Client as StompClient, Message as StompMessage} from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private stompClient: StompClient;

  disconnect() {
    try {
      if (this.stompClient.connected) {
        this.stompClient.disconnect(() => {
        });
      }
    } catch (TypeError) {

    }
  }

  subscribe(queue: string, callback: (message: Message) => void) {
    this.disconnect();
    this.stompClient = Client('ws://localhost:8080/message');
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(`/chat/${queue}`, (msg: StompMessage) => {
        callback(JSON.parse(msg.body));
      });
    });
  }

  sendMessage(message: Message): void {
    fetch('http://localhost:8080/news', {
      method: 'POST',
      body: JSON.stringify({
        from: message.from,
        to: message.to,
        content: message.content,
        date: new Date(),
        queueName: message.queueName
      }),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    }).catch((err: Error) => {
      console.error(err.message);
    });
  }

  async getCorrespondence(from: string, to: string): Promise<Array<Message>>{
    const response: Response | void = await fetch(`http://localhost:8080/news/${from}/${to}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    }).catch((err: Error) => {
      console.error(err.message);
    });
    return response instanceof Response ? <Array<Message>> await response.json() : new Array<Message>();
  }
}
