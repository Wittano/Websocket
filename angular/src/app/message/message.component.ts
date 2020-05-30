import {Component, Input, OnInit} from '@angular/core';
import {Message} from '../models/message';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent implements OnInit {
  @Input() message: Message;
  @Input() username: string;

  constructor() {
  }

  ngOnInit(): void {
  }

  hideDate(element: HTMLParagraphElement) {
    element.style.visibility = element.style.visibility == "visible" ? "hidden" : "visible";
  }

  whoSend(): string {
    return this.message.from == this.username ? "message" : "message-to";
  }
}
