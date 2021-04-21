import Cookies from "js-cookie";

export const createURL = (from?: string, to?: string) => {
  const prefix = window.location.protocol.startsWith("https") ? "wss" : "ws";
  const host =
    process.env.NODE_ENV === "development"
      ? "localhost:8000"
      : window.location.host;

  const chatEndpoint: string = from && to ? `chat/${from}/${to}` : "chat";

  return `${prefix}://${host}/${chatEndpoint}?token=${Cookies.get("access")}`;
};
