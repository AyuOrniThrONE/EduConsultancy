import React, { useEffect, useState } from "react";
import Navbar from "../components/Navbar";
import API from "../utils/api";

const ManageBlogs = () => {
  const [blogs, setBlogs] = useState([]);
  const [editBlogData, setEditBlogData] = useState({
    title: "",
    content: "",
    heroImg: "",
    tagIds: [],
    status: "DRAFT", // Default status
  });
  const [isEditing, setIsEditing] = useState(false);
  const [showDeletePopup, setShowDeletePopup] = useState(false);
  const [blogToDelete, setBlogToDelete] = useState(null);
  const [showCreatePopup, setShowCreatePopup] = useState(false);
  const [tags, setTags] = useState([]);
  const [newBlogData, setNewBlogData] = useState({
    title: "",
    content: "",
    heroImg: "",
    tagIds: [],
    status: "DRAFT", // Default status
  });

  // Fetch all blogs from the service
  const fetchBlogs = async () => {
    try {
      const res = await API.get("/admin/blogs");
      console.log(res.data.blogs);
      setBlogs(res.data.blogs);
    } catch (err) {
      console.error("Failed to fetch blogs", err);
    }
  };

  const fetchTags = async () => {
    try {
      const res = await API.get("/tags");
      console.log(res.data);
      setTags(res.data);
    } catch (err) {
      console.error("Failed to fetch tags", err);
    }
  };

  const createNewBlog = async () => {
    const newBlog = { ...newBlogData };
    try {
      const res = await API.post("/admin/blogs", newBlog);
      setBlogs((prevBlogs) => [...prevBlogs, res.data]);
      setShowCreatePopup(false);
      setNewBlogData({
        title: "",
        content: "",
        heroImg: "",
        tagIds: [],
        status: "DRAFT",
      });
      fetchBlogs();
    } catch (err) {
      console.error("Failed to create blog", err);
    }
  };

  const handleNewBlogChange = (e) => {
    const { name, value } = e.target;
    setNewBlogData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Delete a blog by its ID
  const deleteBlog = async () => {
    try {
      const res = await API.delete(`/admin/blogs/${blogToDelete}`);
      console.log(res);
      setBlogs((prevBlogs) =>
        prevBlogs.filter((blog) => blog.id !== blogToDelete)
      );
      setShowDeletePopup(false);
      setBlogToDelete(null);
    } catch (err) {
      console.error("Failed to delete blog", err);
    }
  };

  const openDeletePopup = (id) => {
    setBlogToDelete(id);
    setShowDeletePopup(true);
  };

  const editBlog = (blog) => {
    setEditBlogData({ ...blog, tagIds: blog.tagIds || [] });
    setIsEditing(true);
  };

  const handleEditBlogChange = (e) => {
    const { name, value } = e.target;
    setEditBlogData((prevData) => ({ ...prevData, [name]: value }));
  };

  const saveEdit = async () => {
    try {
      console.log(editBlogData);
      const res = await API.put(
        `/admin/blogs/${editBlogData.id}`,
        editBlogData
      );
      console.log(res);
      setBlogs((prevBlogs) =>
        prevBlogs.map((blog) => (blog.id === editBlogData.id ? res.data : blog))
      );
      setIsEditing(false);
      setEditBlogData(null);
      fetchBlogs();
    } catch (err) {
      console.error("Failed to update blog", err);
    }
  };

  const handleTagSelection = (id, isEdit = false) => {
    const dataKey = isEdit ? "editBlogData" : "newBlogData";
    const updateData = isEdit ? setEditBlogData : setNewBlogData;

    updateData((prevData) => {
      const tagIds =
        prevData.tagIds && prevData.tagIds.includes(id)
          ? prevData.tagIds.filter((tagId) => tagId !== id)
          : [...prevData.tagIds, id];
      return { ...prevData, tagIds };
    });
  };

  useEffect(() => {
    fetchBlogs();
    fetchTags();
    return () => {
      setBlogs([]); // Cleanup
    };
  }, []);

  return (
    <>
      <Navbar />
      <div className="p-6 bg-gray-100">
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold text-gray-700">Manage Blogs</h1>
          <button
            onClick={() => setShowCreatePopup(true)}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
          >
            Create Blog
          </button>
        </div>

        <div className="overflow-x-auto">
          <table className="table-auto w-full bg-white shadow-md rounded border border-gray-300">
            <thead className="bg-gray-200">
              <tr>
                <th className="px-4 py-2 text-left">#</th>
                <th className="px-4 py-2 text-left">Title</th>
                <th className="px-4 py-2 text-left">Status</th>
                <th className="px-4 py-2 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {blogs.length > 0 ? (
                blogs.map((blog, index) => (
                  <tr key={blog.id} className="border-t">
                    <td className="px-4 py-2">{index + 1}</td>
                    <td className="px-4 py-2">{blog.title}</td>
                    <td className="px-4 py-2">{blog.status}</td>
                    <td className="px-4 py-2 space-x-2">
                      <button
                        onClick={() => editBlog(blog)}
                        className="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-600"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => openDeletePopup(blog.id)}
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
                    colSpan="4"
                    className="px-4 py-2 text-center text-gray-500"
                  >
                    No blogs available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {showCreatePopup && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96">
              <h2 className="text-lg font-bold mb-4">Create Blog</h2>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Title</label>
                <input
                  type="text"
                  name="title"
                  value={newBlogData.title}
                  onChange={handleNewBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Content</label>
                <textarea
                  name="content"
                  value={newBlogData.content}
                  onChange={handleNewBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">
                  Hero Image URL
                </label>
                <input
                  type="text"
                  name="heroImg"
                  value={newBlogData.heroImg}
                  onChange={handleNewBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Tags</label>
                <div>
                  {tags.map((tag) => (
                    <label key={tag.id} className="inline-flex items-center">
                      <input
                        type="checkbox"
                        checked={newBlogData.tagIds.includes(tag.id)}
                        onChange={() => handleTagSelection(tag.id)}
                      />
                      <span className="ml-2 text-black">{tag.tagName}</span>
                    </label>
                  ))}
                </div>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Status</label>
                <select
                  name="status"
                  value={newBlogData.status}
                  onChange={handleNewBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="DRAFT">DRAFT</option>
                  <option value="PUBLISHED">PUBLISHED</option>
                </select>
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowCreatePopup(false)}
                  className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                  Cancel
                </button>
                <button
                  onClick={createNewBlog}
                  className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
                >
                  Create
                </button>
              </div>
            </div>
          </div>
        )}
        {isEditing && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96">
              <h2 className="text-lg font-bold mb-4">Edit Blog</h2>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Title</label>
                <input
                  type="text"
                  name="title"
                  value={editBlogData.title}
                  onChange={handleEditBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Content</label>
                <textarea
                  name="content"
                  value={editBlogData.content}
                  onChange={handleEditBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">
                  Hero Image URL
                </label>
                <input
                  type="text"
                  name="heroImg"
                  value={editBlogData.heroImg}
                  onChange={handleEditBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Tags</label>
                <div>
                  {tags.map((tag) => (
                    <label key={tag.id} className="inline-flex items-center">
                      <input
                        type="checkbox"
                        checked={editBlogData.tagIds.includes(tag.id)}
                        onChange={() => handleTagSelection(tag.id, true)}
                      />
                      <span className="ml-2">{tag.tagName}</span>
                    </label>
                  ))}
                </div>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Status</label>
                <select
                  name="status"
                  value={editBlogData.status}
                  onChange={handleEditBlogChange}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="DRAFT">DRAFT</option>
                  <option value="PUBLISHED">PUBLISHED</option>
                </select>
              </div>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setIsEditing(false)}
                  className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                  Cancel
                </button>
                <button
                  onClick={saveEdit}
                  className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                >
                  Save
                </button>
              </div>
            </div>
          </div>
        )}

        {showDeletePopup && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96">
              <h2 className="text-lg font-bold mb-4">Confirm Delete</h2>
              <p className="text-gray-700 mb-6">
                Are you sure you want to delete this blog?
              </p>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowDeletePopup(false)}
                  className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                  Cancel
                </button>
                <button
                  onClick={() => deleteBlog()}
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

export default ManageBlogs;
