import UserProps from "../../interfaces/props/UserProps";
import { redirect } from "../../utils/Redirect";

export const User = (props: UserProps) => {
  const chat = () => {
    redirect(`/private/${props.username}`);
  };

  return (
    <div className="flex justify-between my-2 duration-500 hover:bg-white hover:text-black hover:cursor-pointer p-2" onClick={chat}>
      <p className="w-full text-lg text-center">{props.username}</p>
    </div>
  );
};
