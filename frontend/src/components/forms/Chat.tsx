import { faPaperPlane } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { useEffect, useState } from "react";
import Message from "../../interfaces/Message";
import ChatProps from "../../interfaces/props/ChatProps";
import { authClient } from "../../utils/HttpClient";
import { refresh } from "../../utils/Refresh";
import { Message as MessageComponent } from "../Message";

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
    <div className="w-full">
      <div>
        <div className="overflow-y-auto flex-row">
          <div className="flex-grow" style={{maxHeight: '90vh'}}>
          {messages.map((e) => (
            <MessageComponent message={e} />
          ))}
          </div>
        </div>
        <div className="sticky flex-grow-0 bottom-0 flex justify-between w-full bg-blue-500">
          <div className="flex justify-center w-full">
          <input
            className="w-1/2 bg-white p-2 m-2 rounded"
            onChange={getMessage}
            type="text"
            placeholder="Send message"
            value={msg}
          />
          </div>
          <button
            className="bg-blue-500 m-1 hover:bg-blue-400 px-3 rounded-full"
            onClick={send}
          >
            <FontAwesomeIcon color="#ffffff" icon={faPaperPlane} />
          </button>
        </div>
      </div>
    </div>
  );
};
