import React, { useEffect, useState } from "react";
import { Chat } from "../components/forms/Chat";
import { User as UserComponent } from "../components/forms/User";
import User from "../interfaces/response/User";
import { authClient } from "../utils/HttpClient";
import { refresh } from "../utils/Refresh";
import { createURL } from "../utils/Socket";

export const ChatPage = () => {
  const [users, setUsers] = useState<Array<User>>([]);
  const [usersFiltered, setUsersFiltred] = useState<Array<User>>([]);
  const socket = new WebSocket(createURL());

  useEffect(() => {
    const getUsers = async () => {
      setUsers(
        await authClient
          .get("/user/list")
          .then((res) => res.data)
          .catch(refresh)
      );
    };

    authClient
      .get("/user/username")
      .then((res) => res.data[0].username)
      .then((username) => localStorage.setItem("username", username));

    getUsers();
  }, []);

  const searchFriend = (e: React.ChangeEvent<HTMLInputElement>) => {
    const newList = users.filter((element) =>
      element.username.startsWith(e.target.value)
    );

    setUsersFiltred(newList);
  };
  /**
   * List of available people, which user can talk to others
   */
  const friends = () => {
    if (usersFiltered.length > 0) {
      return usersFiltered.map((e) => <UserComponent username={e.username} />);
    } else {
      return users.map((e) => <UserComponent username={e.username} />);
    }
  };

  return (
    <div className="flex justify-between">
      <Chat socket={socket} />
      <div>
        {friends()}
        <div>
          <input
            onChange={searchFriend}
            type="text"
            placeholder="Search friend"
          />
        </div>
      </div>
    </div>
  );
};
