import { AuthInput } from "../inputs/AuthInput";
import React, { useState } from "react";
import AuthFormProps from "../../interfaces/props/AuthFormProps";
import Validator from "../../interfaces/Validator";

export const AuthForm = (props: AuthFormProps) => {
  const [isUsernameValid, setUsernameValid] = useState<boolean>(false);
  const [isPasswordValid, setPasswordValid] = useState<boolean>(false);
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string>("");

  const validator = (valid: Validator) => {
    const text = valid.event.target.value;
    let isValid = false;

    if (text.length > valid.max) {
      setError(`${valid.name} is too long`);
    } else if (text.length < valid.min) {
      setError(`${valid.name} is too short`);
    } else {
      isValid = valid.regex.test(text);

      if (isValid) {
        setError("");
      } else {
        setError(`Invalid ${valid.name.toLowerCase()}`);
      }
    }

    valid.setter(isValid);
  };

  const passwordValidator = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);

    validator({
      event: e,
      regex: new RegExp("[\\w!@#$%^&*()]{8,300}"),
      setter: setPasswordValid,
      max: 300,
      min: 8,
      name: "Password",
    });
  };

  const usernameValidator = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(e.target.value);

    validator({
      event: e,
      regex: new RegExp("[A-Z-a-z]{4,50}"),
      setter: setUsernameValid,
      name: "Username",
      min: 4,
      max: 50,
    });
  };

  const click = () => {
    props.onClick({
      username: username,
      password: password,
    });
  };

  const selectError = (propsError: string) => {
    return error !== "" ? error : propsError;
  };

  return (
    <div className="lg:px-40 m-2 grid grid-cols-1 grid-rows-4 shadow border-black">
      <h2 className="text-2xl text-center">{props.name}</h2>
      <AuthInput
        type="text"
        placeholder="username"
        onChange={usernameValidator}
        error={selectError(props.error.fields.login)}
      />
      <AuthInput
        type="password"
        placeholder="password"
        onChange={passwordValidator}
        error={selectError(props.error.fields.password)}
      />
      <div className="text-center">
        <span className="text-red-500">
          {props.name === "Login" ? props.error.login : props.error.register}
        </span>
        <p className="text-green-400 text-lg">{props.message}</p>
      </div>
      <button
        className="mb-14 inline-block px-6 py-2 text-xs font-medium leading-6 text-center disabled:cursor-not-allowed disabled:opacity-50 text-white uppercase transition bg-blue-700 rounded shadow ripple hover:shadow-lg hover:bg-blue-800 focus:outline-none"
        onClick={click}
        disabled={!(isUsernameValid && isPasswordValid)}
      >
        {props.name}
      </button>
    </div>
  );
};
