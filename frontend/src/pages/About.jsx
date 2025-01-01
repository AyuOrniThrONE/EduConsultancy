import React, { useState } from "react";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import FacebookOutlinedIcon from "@mui/icons-material/FacebookOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import MailIcon from "@mui/icons-material/Mail";
import MenuIcon from "@mui/icons-material/Menu";
import Navbar from "../components/Navbar";
import logo from '../assets/logo.png';

export default function About() {
  const [isOpen, setIsOpen] = useState(false);

  // return (
  //   <>
  //     <Navbar />

  //   <section id="about" className="py-16   min-h-[90vh]">
  //     <div className="container mx-auto px-4 text-center">
  //       <h2 className="text-4xl font-bold text-subtitle mb-6 font-cinzel">Our Story</h2>
  //       <p className="text-lg text-subtitle mb-8 ">
  //         At Edu_Consultancy, we are dedicated to empowering students and professionals on their educational journey. 
  //         With a team of experienced consultants, we offer personalized guidance in educational planning, career counseling, 
  //         and training resources to help you achieve your academic and career goals.
  //       </p>
  //       <div className="flex flex-wrap justify-center space-y-4 sm:space-y-0 sm:space-x-6">
  //         <div className="bg-productsBg rounded-lg shadow-lg p-6 w-72 text-left hover:shadow-2xl hover:shadow-gray-600 ease-in duration-200">
  //           <h3 className="text-xl font-semibold text-white mb-2 ">Personalized Planning</h3>
  //           <p className="text-white">
  //             Tailored academic and career plans to align with your unique goals and aspirations.
  //           </p>
  //         </div>
  //         <div className="bg-productsBg rounded-lg shadow-lg p-6 w-72 text-left hover:shadow-2xl hover:shadow-gray-600 ease-in duration-200">
  //           <h3 className="text-xl font-semibold text-white mb-2 ">Expert Guidance</h3>
  //           <p className="text-white">
  //             Work with industry experts to make informed decisions about your educational path.
  //           </p>
  //         </div>
  //         <div className="bg-productsBg rounded-lg shadow-lg p-6 w-72 text-left hover:shadow-2xl hover:shadow-gray-600 ease-in duration-200">
  //           <h3 className="text-xl font-semibold text-white mb-2 ">Resource Access</h3>
  //           <p className="text-white">
  //             Get exclusive access to resources, workshops, and training sessions designed for your success.
  //           </p>
  //         </div>
  //       </div>
  //     </div>
  //   </section>
  //   </>
  // );

  return (
    <>
      <div className="bg-white bg-center bg-no-repeat">
        <Navbar />
        <section className="min-h-[90vh] flex items-center">
          <div className="container mx-auto px-10">
            <div className="flex flex-col md:flex-row items-center">
              <div className="md:w-1/2 mb-8 md:mb-0 px-5">
                <h1 className="text-5xl font-bold mb-4 text-title">About Us</h1>
                <p className="text-lg text-gray-700 mb-6">
                  At Edu_Consultancy, we believe in empowering individuals through education. Our mission is to provide the tools and guidance necessary for personal and professional growth. Whether you're just starting out or looking to advance your skills, we are here to help.
                </p>
                <a
                  href="#contact"
                  className="bg-blue-500 text-white font-semibold py-3 px-8 rounded-full hover:bg-blue-700 transition duration-200"
                >
                  Contact Us
                </a>
              </div>

              <div className="md:w-1/2 px-5">
                <img
                  src={logo}
                  alt="About Us"
                  className="rounded-lg "
                />
              </div>
            </div>
          </div>
        </section>

        {/* Values Section */}
        <section id="values" className="py-16 bg-gray-400">
          <div className="container mx-auto px-6">
            <h2 className="text-3xl font-bold text-center mb-6">Our Values</h2>
            <div className="flex  justify-center gap-8">
              <div className="bg-white shadow-md rounded-lg p-4 max-w-sm">
                <h3 className="text-xl font-semibold mb-2">Integrity</h3>
                <p className="text-gray-700 text-sm">
                We uphold the highest standards of integrity in all our actions. Honesty, transparency, and ethical behavior are the cornerstones of our approach to building lasting relationships with our clients, partners, and community.
                </p>
              </div>

              <div className="bg-white shadow-md rounded-lg p-4 max-w-sm">
                <h3 className="text-xl font-semibold mb-2">Excellence</h3>
                <p className="text-gray-700 text-sm">
                We strive for excellence in every service we provide. Our commitment to continuous improvement and attention to detail ensures that we consistently exceed expectations and deliver unmatched value.
                </p>
              </div>

              <div className="bg-white shadow-md rounded-lg p-4 max-w-sm">
                <h3 className="text-xl font-semibold mb-2">Innovation</h3>
                <p className="text-gray-700 text-sm">
                We embrace innovation to deliver better solutions. By fostering a culture of creativity and forward-thinking, we aim to address challenges with cutting-edge ideas and advanced technologies to shape a better future.
                </p>
              </div>
            </div>
          </div>
        </section>

        {/* Team Section */}
        <section id="team" className="py-16 bg-white">
          <div className="container mx-auto px-10">
            <h2 className="text-3xl font-bold text-center mb-6">Meet Our Team</h2>
            <div className="flex flex-wrap justify-center gap-8">
              <div className="text-center max-w-xs">
                <img
                  src="https://via.placeholder.com/150"
                  alt="Team Member"
                  className="rounded-full mx-auto mb-4"
                />
                <h3 className="text-xl font-semibold">John Doe</h3>
                <p className="text-gray-600 text-sm">Founder & CEO</p>
              </div>

              <div className="text-center max-w-xs">
                <img
                  src="https://via.placeholder.com/150"
                  alt="Team Member"
                  className="rounded-full mx-auto mb-4"
                />
                <h3 className="text-xl font-semibold">Jane Smith</h3>
                <p className="text-gray-600 text-sm">Head of Operations</p>
              </div>

              <div className="text-center max-w-xs">
                <img
                  src="https://via.placeholder.com/150"
                  alt="Team Member"
                  className="rounded-full mx-auto mb-4"
                />
                <h3 className="text-xl font-semibold">Sam Wilson</h3>
                <p className="text-gray-600 text-sm">Lead Consultant</p>
              </div>
            </div>
          </div>
        </section>

        {/* Footer Section */}
        <footer className="bg-gray-800 text-white py-6">
          <div className="container mx-auto px-4 text-center">
            <p className="mb-2">&copy; 2024 Edu_Consultancy. All rights reserved.</p>
            <p>
              Follow us on {" "}
              <a href="#" className="text-blue-400 hover:underline">
                Twitter
              </a>{" "}
              | {" "}
              <a href="#" className="text-blue-400 hover:underline">
                LinkedIn
              </a>
            </p>
          </div>
        </footer>
      </div>
    </>
  );
}
