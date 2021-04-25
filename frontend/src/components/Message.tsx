import { useState } from "react";
import MessageProps from "../interfaces/props/MessageProps";

export const Message = (props: MessageProps) => {
  const [isShowDate, setShowDate] = useState<boolean>(false);
  const date = new Date(props.message.date);

  const isSender =
    props.message.sender.toString() === localStorage.getItem("id")!!;

  const showDate = () => setShowDate(!isShowDate);

  return (
    <div
      className={
        isSender
          ? "rounded text-white bg-blue-500 w-1/3 m-2 p-2"
          : "rounded bg-blue-400 w-1/3 m-2 p-2"
      }
      style={isSender ? { marginLeft: "65%" } : {}}
      onClick={showDate}
    >
      <p key={props.message.id}>{props.message.message}</p>
      <p hidden={!isShowDate}>{date.toUTCString()}</p>
    </div>
  );
};
