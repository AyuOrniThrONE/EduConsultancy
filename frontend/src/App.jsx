import "./App.css";

import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./auth/AuthProvider";
import Login from "./pages/Login";
import Signup from "./pages/Signup";
import ProtectedRoute from "./components/ProtectedRoute";
import HeroComponent from "./components/HeroComponent";
import Products from "./pages/Products";
import Blogs from "./pages/Blogs";
import About from "./pages/About";
import Feedback from "./pages/Feedback";
import Dashboard from "./pages/Dashboard";
import BlogDetail from "./pages/BlogDetail";
import ManageProducts from "./pages/ManageProducts";
import ManageBlogs from "./pages/ManageBlogs";
import PaymentPage from "./pages/PaymentPage";
import ManageFeedbacks from "./pages/ManageFeedbacks";

function App() {
  return (
    <Router>
      <AuthProvider>
        <div className="bg-white  bg-center bg-no-repeat bg-fixed  min-h-[100vh] w-full">
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/" element={<HeroComponent />} />
            <Route path="/products" element={<Products />} />
            <Route path="/contact" element={<HeroComponent />} />
            <Route path="/blogs" element={<Blogs />} />
            <Route path="/about" element={<About />} />
            <Route path="/feedback" element={<Feedback />} />
            <Route path="/blogs/:id" element={<BlogDetail />} />
            <Route path="/forgot-password" element={<BlogDetail />} />
            <Route path="/payments/:id" element={<PaymentPage />} />
            <Route
              path="/profile"
              element={
                <ProtectedRoute roles={["ADMIN", "STUDENT", "PARENT"]}>
                  <Dashboard userRole={"student"} />
                </ProtectedRoute>
              }
            />
            <Route
              path="/admin"
              element={
                <ProtectedRoute roles={["ADMIN"]}>
                  <Dashboard userRole={"admin"} />
                </ProtectedRoute>
              }
            />
            <Route
              path="/manage-blogs"
              element={
                <ProtectedRoute roles={["ADMIN"]}>
                  <ManageBlogs />
                </ProtectedRoute>
              }
            />
            <Route
              path="/manage-products"
              element={
                <ProtectedRoute roles={["ADMIN"]}>
                  <ManageProducts />
                </ProtectedRoute>
              }
            />
            <Route
              path="/manage-feedbacks"
              element={
                <ProtectedRoute roles={["ADMIN"]}>
                  <ManageFeedbacks />
                </ProtectedRoute>
              }
            />
            <Route
              path="/student"
              element={
                <ProtectedRoute roles={["STUDENT"]}>
                  <Dashboard userRole={"student"} />
                </ProtectedRoute>
              }
            />
            <Route
              path="/parent"
              element={
                <ProtectedRoute roles={["PARENT"]}>
                  <Dashboard userRole={"student"} />
                </ProtectedRoute>
              }
            />
            <Route path="*" element={<h1>404 Not Found</h1>} />
          </Routes>
        </div>
      </AuthProvider>
    </Router>
  );
}

export default App;
