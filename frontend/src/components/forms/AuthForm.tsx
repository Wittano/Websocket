import { GeneralInput } from "../inputs/GeneralInput";
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

    if (text.length > valid.max) {
      setError(`${valid.name} is too long`);
    } else if (text.length < valid.min) {
      setError(`${valid.name} is too short`);
    } else {
      const isValid = valid.regex.test(text);

      if (isValid) {
        setError("");
      } else {
        setError(`Invalid ${valid.name.toLowerCase()}`);
      }

      valid.setter(isValid);
    }
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

  const click = (e: React.MouseEvent<HTMLButtonElement>) => {
    props.onClick({
      username: username,
      password: password,
    });
  };

  return (
    <div>
      <h2>{props.name}</h2>
      <GeneralInput
        type="text"
        placeholder="username"
        onChange={usernameValidator}
        error={error}
      />
      <GeneralInput
        type="password"
        placeholder="password"
        onChange={passwordValidator}
        error={error}
      />
      <button onClick={click} disabled={!(isUsernameValid && isPasswordValid)}>
        {props.name}
      </button>
    </div>
  );
};
