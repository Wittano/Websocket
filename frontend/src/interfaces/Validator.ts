import React from "react";

export default interface Validator {
  min: number;
  max: number;
  event: React.ChangeEvent<HTMLInputElement>;
  regex: RegExp;
  setter: React.Dispatch<React.SetStateAction<boolean>>;
  name: string;
}
