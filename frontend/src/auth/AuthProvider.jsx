import React, { createContext, useContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import API from "../utils/api";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const navigate = useNavigate();
  const [authTokens, setAuthTokens] = useState({
    accessToken: localStorage.getItem("accessToken"),
    refreshToken: localStorage.getItem("refreshToken"),
  });

  const setTokens = (accessToken, refreshToken) => {
    setAuthTokens({ accessToken, refreshToken });
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
  };

  const handleLogout = () => {
    setAuthTokens(null);
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    navigate("/login");
  };

  useEffect(() => {
    const accessExpiration = 3600000; // 1 hour
    const refreshExpiration = 604800000; // 7 days

    // Refresh access token before expiration
    const accessTimer = setTimeout(async () => {
      try {
        const response = await API.post("/auth/refresh", {
          refreshToken: authTokens.refreshToken,
        });
        setTokens(response.data.accessToken, authTokens.refreshToken);
        console.log(authTokens);
      } catch {
        console.error("Failed to refresh token. Logging out.");
        handleLogout();
      }
    }, accessExpiration - 60000); // Refresh 1 min before expiry

    // Logout on refresh token expiration
    const refreshTimer = setTimeout(() => {
      console.log(authTokens);
      console.error("Refresh token expired. Logging out.");
      handleLogout();
    }, refreshExpiration);

    return () => {
      clearTimeout(accessTimer);
      clearTimeout(refreshTimer);
    };
  }, [authTokens]);

  return (
    <AuthContext.Provider value={{ authTokens, setTokens, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
