import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getBlogById } from "../services/blogService";

function BlogDetail() {
  const { id } = useParams(); // Extract blog ID from the URL
  const [blog, setBlog] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBlog = async () => {
      try {
        const response = await getBlogById(id);
        setBlog(response);
      } catch (err) {
        setError("Failed to fetch blog data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchBlog();
  }, [id]);

  if (loading) return <p className="text-center text-gray-500">Loading...</p>;
  if (error) return <p className="text-center text-red-500">{error}</p>;

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white shadow-lg rounded-lg">
      <h1 className="text-3xl font-bold mb-4 text-gray-800">{blog.title}</h1>
      {blog.heroImg && (
        <img
          src={blog.heroImg}
          alt={blog.title}
          className="w-full max-h-96 object-cover rounded-lg mb-6"
        />
      )}
      <p className="text-gray-600">
        <strong>Author:</strong> {blog.author}
      </p>
      <p className="text-gray-600 mb-4">
        <strong>Published At:</strong>{" "}
        {new Date(blog.publishedAt).toLocaleDateString()}
      </p>
      <div
        className="text-gray-800 leading-relaxed mb-6"
        dangerouslySetInnerHTML={{ __html: blog.content }}
      />
      {blog.tags && blog.tags.length > 0 && (
        <div className="mt-6">
          <h3 className="text-lg font-semibold text-gray-700">Tags:</h3>
          <div className="flex flex-wrap gap-2 mt-2">
            {blog.tags.map((tag) => (
              <span
                key={tag.id}
                className="px-3 py-1 bg-blue-100 text-blue-700 text-sm rounded-full"
              >
                {tag.tagName}
              </span>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

export default BlogDetail;
