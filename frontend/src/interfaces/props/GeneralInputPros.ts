import React from "react";

export default interface GeneralInputPros {
  type: string;
  placeholder: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error: string;
}
