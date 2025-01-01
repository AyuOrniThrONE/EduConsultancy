import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../utils/api";

const ManageFeedbacks = () => {
  const [feedbacks, setFeedbacks] = useState([]);
  const [showDeletePopup, setShowDeletePopup] = useState(false);
  const [feedbackToDelete, setFeedbackToDelete] = useState(null);

  // Fetch all feedbacks
  const fetchFeedbacks = async () => {
    try {
      const res = await API.get("/admin/feedbacks");
      setFeedbacks(res.data.feedbacks);
    } catch (err) {
      console.error("Failed to fetch feedbacks", err);
    }
  };

  // Delete a feedback by its ID
  const deleteFeedback = async () => {
    try {
      await API.delete(`/admin/feedbacks/${feedbackToDelete}`);
      setFeedbacks((prevFeedbacks) =>
        prevFeedbacks.filter((feedback) => feedback.id !== feedbackToDelete)
      );
      setShowDeletePopup(false);
      setFeedbackToDelete(null);
    } catch (err) {
      console.error("Failed to delete feedback", err);
    }
  };

  const openDeletePopup = (id) => {
    setFeedbackToDelete(id);
    setShowDeletePopup(true);
  };

  useEffect(() => {
    fetchFeedbacks();
    return () => {
      setFeedbacks([]); // Cleanup
    };
  }, []);

  return (
    <>
      <Navbar />
      <div className="p-6 bg-gray-100">
        <h1 className="text-2xl font-bold text-gray-700 mb-4">
          Manage Feedbacks
        </h1>

        <div className="overflow-x-auto">
          <table className="table-auto w-full bg-white shadow-md rounded border border-gray-300">
            <thead className="bg-gray-200">
              <tr>
                <th className="px-4 py-2 text-left">#</th>
                <th className="px-4 py-2 text-left">Name</th>
                <th className="px-4 py-2 text-left">Email</th>
                <th className="px-4 py-2 text-left">Feedback</th>
                <th className="px-4 py-2 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {feedbacks.length > 0 ? (
                feedbacks.map((feedback, index) => (
                  <tr key={feedback.id} className="border-t">
                    <td className="px-4 py-2">{index + 1}</td>
                    <td className="px-4 py-2">{feedback.name}</td>
                    <td className="px-4 py-2">{feedback.email}</td>
                    <td className="px-4 py-2">{feedback.feedback}</td>
                    <td className="px-4 py-2 space-x-2">
                      <button
                        onClick={() => openDeletePopup(feedback.id)}
                        className="px-3 py-1 text-white bg-red-500 rounded hover:bg-red-600"
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td
                    colSpan="5"
                    className="px-4 py-2 text-center text-gray-500"
                  >
                    No feedbacks available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {showDeletePopup && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96">
              <h2 className="text-lg font-bold mb-4">Confirm Delete</h2>
              <p className="text-gray-700 mb-6">
                Are you sure you want to delete this feedback?
              </p>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowDeletePopup(false)}
                  className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                  Cancel
                </button>
                <button
                  onClick={() => deleteFeedback()}
                  className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
                >
                  Delete
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default ManageFeedbacks;
