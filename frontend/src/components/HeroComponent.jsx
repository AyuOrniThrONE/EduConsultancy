import React, { useState } from "react";
import Navbar from "./Navbar";
import { Link } from "react-router-dom";
import mainImg from "../assets/mainImg.png";
import ProductCard from "./ProductCard";

export default function HeroComponent() {
  const courses = [
    {
      title: "React for Beginners",
      description: "Learn React from scratch with hands-on projects.",
      price: "800",
    },
    {
      title: "Mastering JavaScript",
      description: "Deep dive into advanced JavaScript concepts.",
      price: "1000",
    },
    {
      title: "Python Programming",
      description: "Learn Python for data analysis and web development.",
      price: "900",
    },
  ];

  return (
    <>
      <div className="bg-white  bg-center bg-no-repeat">
        <Navbar />
        <section className="min-h-[90vh] flex items-center">
          <div className="w-50  container mx-auto px-10 ">
            <h1 className=" text-7xl text-title font-bold mb-4 ">
              Your Path to Educational Success
            </h1>
            <p className="text-lg mb-6 text-subtitle font-semibold">
              Connect with our expert consultants for personalized guidance on
              educational planning, training, and resources.
            </p>
            <div className="flex justify-start space-x-4">
              <a
                href="#services"
                className="font-semibold py-3 px-8 rounded-full secondarybutton"
              >
                Explore Services
              </a>
              <a
                href="#contact"
                className="font-semibold py-3 px-8 rounded-full actionbutton"
              >
                <Link to="/products">Get Started </Link>
              </a>
            </div>
          </div>

          <div className="w-50 px-5">
            <img
              src={mainImg}
              alt="My Asset"
              style={{ width: "90%", height: "50%" }}
            />
          </div>
          {/* <div className="absolute inset-0 bg-gradient-to-t from-blue-800 to-transparent opacity-50 pointer-events-none"></div> */}
        </section>
      </div>

      {/* Introduction Section */}
      <section id="introduction " className="py-16 bg-gray-100">
        <div className="container mx-auto flex flex-col md:flex-row items-center px-10">
          <div className="md:w-1/2 mx-2">
            <img
              src="https://images.unsplash.com/photo-1498079022511-d15614cb1c02?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
              alt="Introduction"
              className="rounded-lg shadow-lg"
            />
          </div>

          <div className="md:w-1/2 mb-8 md:mb-0 px-5">
            <h2 className="text-3xl font-bold mb-4">Welcome to Our Platform</h2>
            <p className="text-gray-700 text-lg">
              We are dedicated to providing high-quality educational resources
              to help you achieve your goals. Whether you're starting your
              career or upskilling for the future, we have something for
              everyone!
            </p>
          </div>
        </div>
      </section>

      {/* Latest Courses Section */}
      <section id="courses" className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-6">
            Latest Courses
          </h2>
          <div className="flex justify-center px-10">
            <div className="flex space-x-4 ">
              {courses.map((course, index) => (
                <ProductCard product={course} />
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* Footer Section */}
      <footer className="bg-gray-800 text-white py-6">
        <div className="container mx-auto px-4 text-center">
          <p className="mb-2">
            &copy; 2024 Edu_Consultancy. All rights reserved.
          </p>
          <p>
            Follow us on{" "}
            <a href="#" className="text-blue-400 hover:underline">
              Twitter
            </a>{" "}
            |{" "}
            <a href="#" className="text-blue-400 hover:underline">
              LinkedIn
            </a>
          </p>
        </div>
      </footer>
    </>
  );
}
