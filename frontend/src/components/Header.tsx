import { faHome } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import Cookies from "js-cookie";
import { Link } from "react-router-dom";
import HeaderProps from "../interfaces/props/HeaderProps";
import { redirect } from "../utils/Redirect";

export const Header = (props: HeaderProps) => {
    const logout = () => {
        Cookies.remove('access')
        Cookies.remove('refresh')

        localStorage.clear()

        redirect()
    }

  return (
    <div className="bg-blue-500 text-white flex items-baseline justify-between text-xl">
      <Link to="/chat" className="text-5xl py-5 px-3">
        <FontAwesomeIcon icon={faHome} />
      </Link>
      <p className="text-3xl">{props.name}</p>
      <div>
        <button onClick={logout} className="duration-150 p-4 rounded-full hover:bg-white hover:text-black mr-10">
          Logout
        </button>
      </div>
    </div>
  );
};
