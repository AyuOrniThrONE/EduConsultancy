import React from "react";
import Navbar from "../components/Navbar";
import { Link } from "react-router-dom";
import AdminPage from "./AdminPage";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import SettingsIcon from "@mui/icons-material/Settings";
import AssignmentIcon from "@mui/icons-material/Assignment";

export default function Dashboard({ userRole }) {
  // Render the dashboard content based on the user role
  const renderDashboardContent = () => {
    switch (userRole) {
      case "admin":
        return <AdminPage />;
      case "seller":
        return (
          <>
            <h1 className="text-4xl font-bold mb-4">Seller Dashboard</h1>
            <p className="text-lg mb-6">
              Manage your listings, view orders, and analyze sales data.
            </p>
            <div className="flex justify-center space-x-4">
              <Link
                to="/listings"
                className="bg-blue-500  py-2 px-6 rounded-full"
              >
                Manage Listings
              </Link>
              <Link
                to="/orders"
                className="bg-blue-600  py-2 px-6 rounded-full"
              >
                View Orders
              </Link>
              <Link
                to="/sales-analytics"
                className="bg-blue-700  py-2 px-6 rounded-full"
              >
                Sales Analytics
              </Link>
            </div>
          </>
        );
      case "student":
        return (
          <>
            <h1 className="text-4xl font-bold mb-4">Student Dashboard</h1>
            <p className="text-lg mb-6">
              Access your courses, assignments, and track your progress.
            </p>
            <div className="flex justify-center space-x-4">
              <Link
                to="/courses"
                className="bg-blue-500  py-2 px-6 rounded-full actionbutton"
              >
                My Courses
              </Link>
              <Link
                to="/assignments"
                className="bg-blue-600  py-2 px-6 rounded-full actionbutton"
              >
                Assignments
              </Link>
              <Link
                to="/progress"
                className="bg-blue-700  py-2 px-6 rounded-full actionbutton"
              >
                Track Progress
              </Link>
            </div>
          </>
        );
      default:
        return <p className="text-lg text-red-500">Invalid user role.</p>;
    }
  };

  return (
    <>
      <Navbar />
      <section className=" min-h-[90vh] flex py-5 justify-center">
        <div className="container mx-auto text-center px-4">
          {renderDashboardContent()}
        </div>
      </section>
    </>
  );
}
