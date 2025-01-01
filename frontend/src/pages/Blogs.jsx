import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import { getBlogs } from "../services/blogService";
import API from "../utils/api";

export default function Blogs() {
  const [blogData, setBlogData] = useState([]);
  const [loading, setLoading] = useState(true); // State for loading
  const [error, setError] = useState(null); // State for error

  const fetchBlogs = async () => {
    try {
      const res = await API.get("/blogs");
      setBlogData(res.data.blogs);
    } catch (err) {
      console.log(err);
      setError(err.message || "An error occurred. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBlogs();

    return () => {
      setBlogData([]);
    };
  }, []);

  if (loading) {
    return (
      <>
        <Navbar />
        <div className="flex items-center justify-center bg-gray-100">
          <div className="text-center">
            <div className="loader mb-4"></div>{" "}
            {/* Add your loader animation */}
            <p className="text-gray-600">Loading blogs...</p>
          </div>
        </div>
      </>
    );
  }

  if (error) {
    return (
      <>
        <Navbar />
        <div className="flex items-center justify-center bg-gray-100">
          <div className="text-center">
            <h2 className="text-2xl font-semibold text-red-500 mb-4">Error</h2>
            <p className="text-gray-600">{error}</p>
          </div>
        </div>
      </>
    );
  }

  return (
    <>
      <Navbar />
      <section className="py-12 min-h-[71vh]">
        <div className="container mx-auto px-4">
          <h2 className="text-4xl font-semibold text-center mb-8 text-subtitile font-cinzel">
            Insights
          </h2>
          <div className="grid md:grid-cols-3 gap-8">
            {blogData.map((blog) => (
              <Link
                to={`/blogs/${blog.id}`}
                key={blog.id}
                className="bg-white shadow-md rounded-lg overflow-hidden hover:shadow-xl transition-shadow duration-300"
              >
                <img
                  src={blog.heroImg}
                  alt={blog.title}
                  className="w-full h-48 object-cover"
                />
                <div className="p-6">
                  <h3 className="text-2xl font-semibold mb-3 text-gray-800">
                    {blog.title}
                  </h3>
                  <p className="text-gray-600 mb-4">{blog.summary}</p>
                </div>
              </Link>
            ))}
          </div>
        </div>
      </section>

      {/* Footer with "Create Your Blog" Button */}
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

      {/* Popup Modal for Blog Creation */}
      {/* {isCreateBlogOpen && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
          <div className="bg-white p-6 rounded-lg shadow-lg max-w-xl relative">
            <button
              onClick={handleCloseCreateBlog}
              className="absolute top-3 right-3 text-gray-500 hover:text-gray-700"
            >
              <span className="material-icons">
                <Close />
              </span>
            </button>
            <h3 className="text-2xl font-semibold mb-4 text-gray-800">
              Create Your Blog
            </h3>
            <form>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Title</label>
                <input
                  type="text"
                  required
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Enter blog title"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Content</label>
                <input
                  type="text"
                  required
                  className="w-full border rounded-lg h-50 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                  placeholder="Write your blog content here..."
                  rows="6"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Attach Files</label>
                <input
                  type="file"
                  className="w-full border rounded-lg px-4 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
              </div>
              <div className="flex justify-end">
                <button
                  type="button"
                  onClick={handleCloseCreateBlog}
                  className="bg-gray-500 text-white px-4 py-2 rounded-lg hover:bg-gray-600 mr-2"
                >
                  Discard
                </button>
                <button
                  type="submit"
                  className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600"
                >
                  Submit
                </button>
              </div>
            </form>
          </div>
        </div>
      )} */}
    </>
  );
}
