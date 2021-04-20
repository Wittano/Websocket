import Cookies from "js-cookie";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ChatPage } from "./pages/ChatPage";
import { HomePage } from "./pages/HomePage";
import { redirect } from "./utils/Redirect";

export const App = () => {
  const auth = () => {
    if (Cookies.get("access")) {
      return <Route component={ChatPage} path="/chat" />;
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
