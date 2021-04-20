export interface Message {
    id: number;
    sender: number;
    receiver?: any;
    message: string;
    date: Date;
}