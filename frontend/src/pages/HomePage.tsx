import Cookies from "js-cookie";
import { useState } from "react";
import { AuthForm } from "../components/forms/AuthForm";
import AuthError from "../interfaces/form/AuthError";
import TokenReposen from "../interfaces/response/TokenReposne";
import UserData from "../interfaces/UserData";
import { authClient, client } from "../utils/HttpClient";
import { redirect } from "../utils/Redirect";

export const HomePage = () => {
  if (Cookies.get("access")) {
    redirect("/chat");
  }

  const [message, setMessage] = useState<string>("");
  const [error, setError] = useState<AuthError>({
    login: "",
    register: "",
    fields: {
      login: "",
      password: "",
    },
  });

  const register = (user: UserData) => {
    client
      .post("/user/", user)
      .then(() => {
        setMessage("Successful sign up!");
        login(user);
      })
      .catch((err) => {
        setMessage("");
        setError({
          register: err.response.data.detail,
          login: "",
          fields: {
            login: "",
            password: "",
          },
        });
      });
  };

  const login = (user: UserData) => {
    client
      .post("/token", user)
      .then((res) => {
        const tokens: TokenReposen = res.data;
        let date = new Date();
        date.setHours(date.getHours() + 1);

        const isHttps: boolean = window.location.protocol.startsWith("https");

        Cookies.set("access", tokens.access, {
          expires: date,
          secure: isHttps,
        });

        date.setHours(date.getHours() + 23);

        Cookies.set("refresh", tokens.refresh, {
          expires: date,
          secure: isHttps,
        });

        authClient.get("/user/username").then((res) => {
          localStorage.setItem("username", res.data[0].username);
          localStorage.setItem("id", res.data[0].id);
        });

        redirect("/chat");
      })
      .catch((err) => {
        setError({
          login: err.response.data.detail,
          register: "",
          fields: {
            login: "",
            password: "",
          },
        });
      });
  };

  return (
    <div>
      <h1 className="text-3xl text-center mt-3">Chat</h1>
      <div className="flex justify-between justify-items-center m-10">
        <AuthForm
          name="Register"
          onClick={register}
          error={error}
          message={message}
        />
        <AuthForm name="Login" onClick={login} error={error} />
      </div>
    </div>
  );
};
