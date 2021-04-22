import MessageProps from "../interfaces/props/MessageProps";

export const Message = (props: MessageProps) => {
  const date = new Date(props.message.date);

  const isSender =
    props.message.sender.toString() === localStorage.getItem("id")!!;

  return (
    <div
      className={
        isSender
          ? "rounded text-white bg-blue-500 w-1/3 m-2 p-2"
          : "rounded bg-blue-400 w-1/3 m-2 p-2"
      }

      style={isSender ? {marginLeft: '65%'} : {}}
    >
      <p key={props.message.id}>{props.message.message}</p>
      <p>{date.toUTCString()}</p>
    </div>
  );
};
