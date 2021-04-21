import React, { useEffect, useState } from "react";
import Message from "../../interfaces/Message";
import ChatProps from "../../interfaces/props/ChatProps";
import { authClient } from "../../utils/HttpClient";
import { refresh } from "../../utils/Refresh";

export const Chat = (props: ChatProps) => {
  const [msg, setMsg] = useState<string>("");
  const [messages, setMessages] = useState<Message[]>([]);

  props.socket.onmessage = (e) => {
    setMessages([...messages, JSON.parse(e.data)]);
  };

  useEffect(() => {
    const getMessages = async () => {
      const url = props.from && props.to ? `/message/${props.to}` : "/message/";

      setMessages(
        await authClient
          .get(url)
          .then((res) => res.data)
          .catch(refresh)
      );
    };

    getMessages();
  }, []);

  const send = () => {
    props.socket.send(msg);
    setMsg("");
  };

  const getMessage = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMsg(e.target.value);
  };
  return (
    <div>
      <div>
        <div>
          {messages.map((e, i) => (
            <p key={i}>{e.message}</p>
          ))}
        </div>
        <div>
          <input
            onChange={getMessage}
            type="text"
            placeholder="Send message"
            value={msg}
          />
          <button onClick={send}>Send</button>
        </div>
      </div>
    </div>
  );
};
