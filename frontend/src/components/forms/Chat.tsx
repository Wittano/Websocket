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

  props.socket.onopen = () => {
    console.log("You're online!");
  };

  props.socket.onclose = () => {
    console.log("You're offline!");
  };

  props.socket.onmessage = (e) => {
    const data = JSON.parse(e.data);

    if (
      messages.find(
        (e) =>
          e.message === data.message &&
          e.date.toString() === data.date.toString()
      )
    ) {
      setMessages([...messages, data]);
    }
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

  const message = () => messages.map((e) => <MessageComponent message={e} />);

  return (
    <div className="w-full">
      <div>
        <div className="overflow-y-auto">
          <div
            id="msg-container"
            style={{ maxHeight: "85vh", scrollBehavior: "smooth" }}
          >
            {message()}
          </div>
        </div>
        <div className=" sticky bottom-0 w-full flex justify-between bg-blue-500">
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
