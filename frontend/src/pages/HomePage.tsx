import Cookies from "js-cookie";
import { AuthForm } from "../components/forms/AuthForm";
import TokenReposen from "../interfaces/response/TokenReposne";
import UserData from "../interfaces/UserData";
import { client } from "../utils/HttpClient";

export const HomePage = () => {
  const register = (user: UserData) => {
    client.post("/user/", user).then(() => {
      login(user);
    });
  };

  const login = (user: UserData) => {
    client.post("/token/", user).then((res) => {
      const tokens: TokenReposen = res.data;
      const date = new Date();
      date.setHours(date.getHours() + 1);

      const isHttps: boolean = window.location.protocol.startsWith("https");

      Cookies.set("access", tokens.access, {
        expires: date.getDate(),
        secure: isHttps,
      });

      date.setHours(date.getHours() + 23);

      Cookies.set("refresh", tokens.refresh, {
        expires: date.getDate(),
        secure: isHttps,
      });
    });
  };

  return (
    <div>
      <h1>Hello Chat</h1>
      <AuthForm name="Register" onClick={register} />
      <AuthForm name="Login" onClick={login} />
    </div>
  );
};
