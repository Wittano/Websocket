import React, { useEffect, useState } from "react";
import { Chat } from "../components/forms/Chat";
import { User as UserComponent } from "../components/forms/User";
import { Header } from "../components/Header";
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
      .then((res) => res.data[0])
      .then((data) => {
        localStorage.setItem("username", data.username);
        localStorage.setItem("id", data.id);
      });

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
    <div>
      <Header name={localStorage.getItem("username")!!} />
      <div className="flex justify-between">
        <div className="bg-blue-500 text-white">
          <h2 className="text-2xl text-center">Users</h2>
          <div className="overflow-y-auto h-screen">{friends()}</div>
          <div className="sticky bottom-0">
            <input
              className="text-center text-lg text-black"
              onChange={searchFriend}
              type="text"
              placeholder="Search friend"
            />
          </div>
        </div>
        <Chat socket={socket} />
      </div>
    </div>
  );
};
