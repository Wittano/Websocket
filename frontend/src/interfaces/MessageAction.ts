import Message from "./Message";

export enum TypeAction {
  ADD,
}

export default interface MessageAction {
  type: TypeAction;
  change: Message;
}
