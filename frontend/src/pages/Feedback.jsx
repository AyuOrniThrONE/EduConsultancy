import React, { useState } from "react";

import Navbar from "../components/Navbar";

import API from "../utils/api";

const Feedback = () => {
  const [inputs, setInputs] = useState({
    name: "",
    email: "",
    feedback: "",
  });
  const [showPopup, setShowPopup] = useState(false);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setInputs((prev) => ({ ...prev, [name]: value }));
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    // Send feedback to the server
    const res = await API.post("/feedback", inputs);
    if (!res.ok) {
      return alert("Failed to submit feedback");
    }
    // Reset the form and show the success popup
    setInputs({
      name: "",
      email: "",
      feedback: "",
    });
    setShowPopup(true);
  };

  return (
    <>
      <Navbar />
      <div className="flex items-center justify-center min-h-screen">
        <div className="w-[800px] bg-white rounded-lg shadow-lg overflow-hidden flex">
          {/* Left Side - Feedback Form */}
          <div className="w-1/2 p-8">
            <h2 className="text-4xl font-semibold text-purple-600 mb-4">
              Feedback
            </h2>
            <p className="mb-6 text-gray-600">
              We value your feedback! Please let us know your thoughts.
            </p>
            <form onSubmit={handleSubmit} className="space-y-4">
              <input
                type="text"
                placeholder="Your Name"
                name="name"
                value={inputs.name}
                onChange={handleChange}
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
                required
              />
              <input
                type="email"
                placeholder="Your Email"
                name="email"
                value={inputs.email}
                onChange={handleChange}
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
                required
              />
              <textarea
                placeholder="Your Feedback"
                name="feedback"
                value={inputs.feedback}
                onChange={handleChange}
                rows="5"
                className="w-full px-4 py-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-purple-500"
                required
              ></textarea>
              <button
                type="submit"
                className="w-full px-4 py-2 bg-purple-600 text-white rounded-md font-semibold hover:bg-purple-700"
              >
                Submit Feedback
              </button>
            </form>
          </div>

          {/* Right Side - Background Image */}
          <div className="w-1/2 bg-purple-200 relative">
            <img
              src="https://plus.unsplash.com/premium_vector-1682309004442-a44a419e1b09?w=600&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzR8fGZlZWRiYWNrfGVufDB8fDB8fHww"
              alt="Feedback"
              className="h-full w-full object-cover"
            />
            <div className="absolute inset-0 bg-black opacity-40"></div>
            <div className="absolute inset-0 flex flex-col justify-center items-center text-white px-8 text-center">
              <h2 className="text-3xl font-bold">Your Opinion Matters</h2>
              <p className="mt-2">
                Help us improve by sharing your valuable feedback.
              </p>
            </div>
          </div>
        </div>

        {/* Success Popup */}
        {showPopup && (
          <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white p-6 rounded-lg shadow-lg text-center">
              <h3 className="text-2xl font-semibold text-green-600">
                Thank You!
              </h3>
              <p className="mt-4">We appreciate your feedback.</p>
              <button
                onClick={() => setShowPopup(false)}
                className="mt-6 px-4 py-2 bg-purple-600 text-white rounded-md font-semibold hover:bg-purple-700"
              >
                Close
              </button>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default Feedback;
