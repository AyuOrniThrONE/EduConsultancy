import React from "react";
import { Link } from "react-router-dom";

import Navbar from "../components/Navbar";

const AdminPage = () => {
  return (
    <>
      <h1 className="text-4xl font-bold mb-4">Admin Dashboard</h1>
      <p className="text-lg mb-6">
        Manage users, view analytics, and configure settings.
      </p>
      <div className="flex justify-center space-x-4">
        <Link
          to="/manage-products"
          className="bg-blue-500  py-2 px-6 rounded-full actionbutton"
        >
          Manage Products
        </Link>
        <Link
          to="/manage-blogs"
          className="bg-blue-600  py-2 px-6 rounded-full actionbutton"
        >
          Manage Blogs
        </Link>
        <Link
          to="/manage-feedbacks"
          className="bg-blue-700  py-2 px-6 rounded-full actionbutton"
        >
          View Feedbacks
        </Link>
      </div>
    </>
  );
};

export default AdminPage;
