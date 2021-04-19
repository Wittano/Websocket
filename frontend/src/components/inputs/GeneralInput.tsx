import GeneralInputPros from "../../interfaces/props/GeneralInputPros";

export const GeneralInput = (props: GeneralInputPros) => {
  let error: string = "";

  return (
    <div>
      <input placeholder={props.placeholder} type={props.type} onChange={props.onChange}/>
      <span>{error}</span>
    </div>
  );
};
