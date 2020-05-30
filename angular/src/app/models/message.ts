export class Message {
  content: string;
  date: Date;
  from: string;
  to: string;
  queueName: string;

  constructor(
    from: string,
    to: string,
    content: string,
    date: Date = new Date(),
    queueName: string
  ) {
    this.from = from;
    this.to = to;
    this.content = content;
    this.date = date;
    this.queueName = queueName;
  }
}
