import Cookies from "js-cookie";
import { client } from "./HttpClient";
import { redirect } from "./Redirect";

/**
 * Refresh JWT token. If 'refresh' token dosen't exist, user'll redirect to login page'
 */
export const refresh = () => {
  if (!Cookies.get("access")) {
    if (Cookies.get("refresh")) {
      client
        .post("/token/refresh", {
          refresh: Cookies.get("refresh"),
        })
        .then(async (res) => {
          let date = new Date();
          date.setHours(date.getHours() + 1);

          const isHttps = window.location.protocol.startsWith("https");

          // Save new JWT token
          Cookies.set("access", res.data.access, {
            expires: date,
            secure: isHttps,
          });

          redirect("/chat");
        });
    } else if (window.location.pathname !== "/") {
      redirect();
    } else if (window.location.pathname === "/" && Cookies.get("access")) {
      redirect("/chat");
    }
  }
};
