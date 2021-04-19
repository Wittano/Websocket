import axios from "axios";
import * as Cookies from "js-cookie";

const url =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8000"
    : window.location.origin;

export const client = axios.create({
  baseURL: `${url}/api`,
});

export const authClient = axios.create({
  baseURL: `${url}/api`,
  headers: {
    Authorization: Cookies.get("token"),
  },
});
