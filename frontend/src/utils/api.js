import axios from "axios";
import { useAuth } from "../auth/AuthProvider";

const API = axios.create({
  baseURL: "http://localhost:8080",
});

// Utility Functions for Token Management
const getAccessToken = () => localStorage.getItem("accessToken");
const getRefreshToken = () => localStorage.getItem("refreshToken");
const setAccessToken = (token) => localStorage.setItem("accessToken", token);
const clearTokens = () => {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
};

// Request Interceptor to Add Authorization Header
API.interceptors.request.use((config) => {
  const accessToken = getAccessToken();
  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }
  return config;
});

// Response Interceptor to Handle Token Expiration
API.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // Check if error is 401 and the request hasn't been retried yet
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      const refreshToken = getRefreshToken();
      if (!refreshToken) {
        console.error("Refresh token missing. Logging out...");
        clearTokens();
        return Promise.reject(error);
      }

      try {
        // Refresh the access token
        const response = await axios.post(
          "http://localhost:8080/auth/refresh",
          {},
          {
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          }
        );

        const { accessToken } = response.data;
        setAccessToken(accessToken);

        // Retry the original request with the new access token
        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        return API(originalRequest);
      } catch (err) {
        console.error("Failed to refresh token. Logging out...");
        clearTokens();

        // Use AuthProvider's logout if needed
        const { handleLogout } = useAuth();
        if (handleLogout) {
          handleLogout();
        }

        return Promise.reject(err);
      }
    }

    return Promise.reject(error);
  }
);

export default API;
