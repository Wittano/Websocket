import GeneralInputPros from "../../interfaces/props/GeneralInputPros";

export const AuthInput = (props: GeneralInputPros) => {
  let error: string = "";

  return (
    <div className="m-3">
      <input
        className="border-2 border-solid border-black p-3"
        placeholder={props.placeholder}
        type={props.type}
        onChange={props.onChange}
      />
      <span>{error}</span>
    </div>
  );
};
