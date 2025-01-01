import React from "react";
import { Link } from "react-router-dom";

const ForgotPassword = () => {
  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="w-[800px] bg-white rounded-lg shadow-lg overflow-hidden flex">
        {/* Left Side - Image and Welcome Text */}
        <div className="w-1/2 bg-purple-200 relative">
          <img
            src="https://media.istockphoto.com/id/2124263900/photo/privacy-signing-in-concept-3d-glossy-icon-of-password-input-symbol-3d-render.webp?a=1&b=1&s=612x612&w=0&k=20&c=ofxKZggMkWBjfFH1ewOvWeW0lGJ7GftJ2zaIc-MQwmo="
            alt="Forgot Password"
            className="h-full w-full object-cover"
          />
          <div className="absolute inset-0 bg-black opacity-40"></div>
          <div className="absolute inset-0 flex flex-col justify-center items-center  px-8 text-center">
            <h2 className="text-3xl font-bold">Reset Password</h2>
            <p className="mt-2">
              Enter your email to receive a password reset link.
            </p>
            <Link
              to="/login"
              className="mt-6 px-6 py-2 bg-white text-purple-600 rounded-md font-semibold hover:bg-gray-100"
            >
              Back to Login
            </Link>
          </div>
        </div>

        {/* Right Side - Forgot Password Form */}
        <div className="w-1/2 p-8">
          <h2 className="text-4xl font-semibold text-purple-600 mb-4">
            Forgot Password
          </h2>
          <form className="space-y-4">
            <input
              type="email"
              placeholder="Email"
              className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
              required
            />
            <button
              type="submit"
              className="w-full px-4 py-2 bg-purple-600  rounded-md font-semibold hover:bg-purple-700"
            >
              Send Reset Link
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
