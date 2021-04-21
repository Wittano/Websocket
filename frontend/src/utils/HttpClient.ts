import axios from "axios";
import * as Cookies from "js-cookie";

const url =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8000"
    : window.location.origin;

/**
 * Base http client for API
 */
export const client = axios.create({
  baseURL: `${url}/api`,
});

/**
 * Base http client for authorizted API
 */
export const authClient = axios.create({
  baseURL: `${url}/api`,
  headers: {
    Authorization: `Bearer ${Cookies.get("access")}`,
  },
});
