import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import FacebookOutlinedIcon from "@mui/icons-material/FacebookOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import MailIcon from "@mui/icons-material/Mail";
import MenuIcon from "@mui/icons-material/Menu";

import { Link } from "react-router-dom";
import logo from "../assets/logo.png";

import { useAuth } from "../auth/AuthProvider";

export default function Navbar() {
  const { handleLogout } = useAuth();

  const [isOpen, setIsOpen] = useState(false);

  const navigate = useNavigate();
  // Toggle function to show/hide menu
  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  const token = localStorage.getItem("accessToken"); // Retrieve the token from local storage

  return (
    <div className="min-h-[10vh]  px-4 py-2 grid grid-cols-3 gap-4 content-center">
      <div className="italic text-title	font-great-vibes text-4xl px-5 font-semibold content-center">
        <img
          src={logo}
          alt="My Asset"
          style={{ width: "75px", height: "75px" }}
        />
      </div>
      <div className="col-span-2 flex items-center justify-end text-nav">
        <Link className="px-4 py-3 hover:pb-5 " to="/">
          Home
        </Link>
        <Link className="px-4 py-3 hover:pb-5 ease-in duration-200" to="/about">
          About
        </Link>

        <Link
          className="px-4 py-3 hover:pb-5 ease-in duration-200"
          to="/products"
        >
          Products
        </Link>
        <Link className="px-4 py-3 hover:pb-5 ease-in duration-200" to="/blogs">
          Blogs
        </Link>

        <Link
          className="px-4 py-3 hover:pb-5 ease-in duration-200"
          to="/feedback"
        >
          Feedback
        </Link>
        {token ? (
          <Link
            className="px-4 py-3 hover:pb-5 ease-in duration-200"
            to="/profile"
          >
            <AccountCircleIcon />
          </Link>
        ) : (
          <Link
            to="/login"
            className="px-4 py-2 rounded-md font-semibold mx-4 text-white actionbutton"
          >
            Login
          </Link>
        )}

        {token && (
          <button
            onClick={handleLogout}
            className="px-4 py-2 rounded-md font-semibold mx-4 text-white actionbutton"
          >
            LogOut
          </button>
        )}

        <Link className="rounded-md px-4 py-2 secondarybutton" to="/signup">
          Sign Up
        </Link>
        {/* <button className="px-4">
          <MailIcon />
        </button>
        <button className="px-4">
          <LocalPhoneIcon />
        </button> */}
      </div>
    </div>
  );
}
