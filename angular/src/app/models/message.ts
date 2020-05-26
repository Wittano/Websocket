export class Message {
  content: string;
  date: Date;
  from: string;
  to: string;

  constructor(from: string, to: string, content: string, date: Date) {
    this.from = from;
    this.to = to;
    this.content = content;
    this.date = date;
  }
}
