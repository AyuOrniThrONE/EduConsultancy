import React from "react";
import { Navigate } from "react-router-dom";
import { getUser } from "../services/authService";

const ProtectedRoute = ({ children, roles }) => {
  const user = getUser();

  // Check if the user exists
  if (!user) return <Navigate to="/login" />;

  // Check if the user has the required roles
  const userRoles = user.roles || []; // Ensure roles is an array
  if (roles && !roles.some((role) => userRoles.includes(role))) {
    return <Navigate to="/unauthorized" />;
  }

  return children;
};

export default ProtectedRoute;
