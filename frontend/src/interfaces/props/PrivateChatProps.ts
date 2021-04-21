import { RouteComponentProps } from "react-router";

interface MatchProps {
  name?: string;
}

export default interface PrivateChatProps
  extends RouteComponentProps<MatchProps> {}
