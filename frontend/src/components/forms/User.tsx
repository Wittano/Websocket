import UserProps from "../../interfaces/props/UserProps";
import { redirect } from "../../utils/Redirect";

export const User = (props: UserProps) => {
  const chat = () => {
    redirect(`/private/${props.username}`);
  };

  return (
    <div>
      <p>{props.username}</p>
      <button onClick={chat}>Chat</button>
    </div>
  );
};
