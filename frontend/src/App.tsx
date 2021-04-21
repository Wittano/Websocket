import Cookies from "js-cookie";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ChatPage } from "./pages/ChatPage";
import { HomePage } from "./pages/HomePage";
import { PrivateChatPage } from "./pages/PrivateChatPage";
import { redirect } from "./utils/Redirect";
import { refresh } from "./utils/Refresh";

export const App = () => {
  /**
   * Authorizted Routes
   */
  const auth = () => {
    refresh();

    if (Cookies.get("access")) {
      return (
        <span>
          <Route component={ChatPage} path="/chat" />
          <Route component={PrivateChatPage} path="/private/:name" />
        </span>
      );
    }
  };

  return (
    <BrowserRouter>
      <Switch>
        <Route exact component={HomePage} path="/" />
        {auth()}
        <Route
          path="*"
          component={() => {
            redirect();
            return <p>Not Found</p>;
          }}
        />
      </Switch>
    </BrowserRouter>
  );
};
