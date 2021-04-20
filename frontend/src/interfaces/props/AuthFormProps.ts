import AuthError from "../form/AuthError";
import UserData from "../UserData";

export default interface AuthFormProps {
  name: string;
  onClick: (user: UserData) => void;
  error: AuthError;
}
