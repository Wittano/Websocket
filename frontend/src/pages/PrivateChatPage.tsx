import { Chat } from "../components/forms/Chat";
import { Header } from "../components/Header";
import PrivateChatProps from "../interfaces/props/PrivateChatProps";
import { createURL } from "../utils/Socket";

export const PrivateChatPage = (props: PrivateChatProps) => {
  const { name } = props.match.params;
  const username = localStorage.getItem("username")!!;
  const socket = new WebSocket(createURL(username, name));

  return (
    <div>
      <Header name={name!!}/>
      <Chat socket={socket} from={username} to={name} />
    </div>
  );
};
