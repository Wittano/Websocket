import Cookies from "js-cookie";
import React, { useEffect, useReducer, useState } from "react";
import Message from "../interfaces/Message";
import { authClient } from "../utils/HttpClient";

export const ChatPage = () => {
  const [msg, setMsg] = useState<string>("");
  const [messages, setMessages] = useState<Message[]>([]);
  const prefix = window.location.protocol.startsWith("https") ? "wss" : "ws";
  const host =
    process.env.NODE_ENV === "development"
      ? "localhost:8000"
      : window.location.host;
  const socket = new WebSocket(
    `${prefix}://${host}/chat?token=${Cookies.get("access")}`
  );

  socket.onmessage = (e) => {
    setMessages([...messages, JSON.parse(e.data)]);
  };

  useEffect(() => {
    const getMessages = async () => {
      setMessages(await authClient.get("/message/").then((res) => res.data));
    };
    getMessages();
  }, []);

  const send = () => {
    socket.send(msg);
    setMsg("");
  };

  const getMessage = (e: React.ChangeEvent<HTMLInputElement>) => {
    setMsg(e.target.value);
  };

  return (
    <div>
      <h1>Chat</h1>
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
  );
};
