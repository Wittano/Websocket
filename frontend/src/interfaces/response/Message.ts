export interface Message {
    id: number;
    sender: number;
    receiver?: number;
    message: string;
    date: Date;
}