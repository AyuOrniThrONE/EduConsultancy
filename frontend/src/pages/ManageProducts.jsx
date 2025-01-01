import React, { useEffect, useState } from "react";
import API from "../utils/api.js";
import Navbar from "../components/Navbar";

import {
  getFullProducts,
  updateProduct,
  deleteProductById,
  createProduct,
} from "../services/productService";
import { getCategories } from "../services/productService";

const ManageProducts = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [editProductData, setEditProductData] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [showDeletePopup, setShowDeletePopup] = useState(false);
  const [productToDelete, setProductToDelete] = useState(null);
  const [showCreatePopup, setShowCreatePopup] = useState(false);
  const [newProductData, setNewProductData] = useState({
    title: "",
    description: "",
    summary: "",
    categoryId: "",
    price: "",
    status: "ACTIVE", // Default status
  });

  // Fetch all products from the service
  const fetchProducts = async () => {
    try {
      const res = await API.get("/admin/courses");
      const data = await res.data;
      setProducts(data.courses);
    } catch (err) {
      console.error("Failed to fetch products", err);
    }
  };
  // Fetch all categories from the service
  const fetchCategories = async () => {
    try {
      const res = await API.get("/categories");
      setCategories(res.data);
    } catch (err) {
      console.error("Failed to fetch categories", err);
    }
  };

  const createProducts = async () => {
    const newProduct = {
      ...newProductData,
    };

    console.log(newProduct);
    const res = await API.post("/admin/courses", newProduct);
    setProducts((prevProducts) => [...prevProducts, res.data]);
    setShowCreatePopup(false);
    setNewProductData({
      title: "",
      description: "",
      summary: "",
      categoryId: "",
      price: "",
      status: "ACTIVE", // Reset to default
    });
  };

  const handleNewProductChange = (e) => {
    const { name, value } = e.target;
    setNewProductData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Delete a product by its ID
  const deleteProduct = async () => {
    const res = await API.delete(`/admin/courses/${productToDelete}`);
    setProducts((prevProducts) =>
      prevProducts.filter((product) => product.id !== productToDelete)
    );
    setShowDeletePopup(false);
    setProductToDelete(null);
  };

  // Open the delete confirmation popup
  const openDeletePopup = (id) => {
    setProductToDelete(id);
    setShowDeletePopup(true);
  };

  // Open the edit modal with the product data
  const editProduct = (product) => {
    setEditProductData({ ...product });
    setIsEditing(true);
  };

  // Handle input change for edit form
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditProductData((prevData) => ({ ...prevData, [name]: value }));
  };

  // Handle category change
  const handleCategoryChange = (e) => {
    setEditProductData((prevData) => ({
      ...prevData,
      category: e.target.value,
    }));
  };
  const handleCategoryChangeCreate = (e) => {
    const selectedCategoryId = e.target.value;
    setNewProductData((prevData) => ({
      ...prevData,
      categoryId: selectedCategoryId,
    }));
  };
  const handleStatusChange = (e) => {
    setEditProductData((prevData) => ({
      ...prevData,
      status: e.target.value,
    }));
  };

  // Save the edited product
  const saveEdit = async () => {
    const data = {
      ...editProductData,
      categoryId: editProductData.category.id
        ? editProductData.category.id
        : editProductData.category,
    };
    delete data.category;
    console.log(data);
    const res = await API.put(`/admin/courses/${editProductData.id}`, data);
    setProducts((prevProducts) =>
      prevProducts.map((product) =>
        product.id === editProductData.id ? editProductData : product
      )
    );
    setIsEditing(false);
    setEditProductData(null);
  };

  useEffect(() => {
    fetchProducts();
    fetchCategories();
    return () => {
      setProducts([]); // Cleanup
      setCategories([]);
    };
  }, []);

  return (
    <>
      <Navbar />
      <div className="p-6 bg-gray-100">
        <div className="flex justify-between items-center mb-4">
          <h1 className="text-2xl font-bold text-gray-700">Manage Products</h1>
          <button
            onClick={() => setShowCreatePopup(true)}
            className="px-4 py-2 bg-green-500 text-white rounded hover:bg-green-600"
          >
            Create Product
          </button>
        </div>
        <div className="overflow-x-auto">
          <table className="table-auto w-full bg-white shadow-md rounded border border-gray-300">
            <thead className="bg-gray-200">
              <tr>
                <th className="px-4 py-2 text-left">#</th>
                <th className="px-4 py-2 text-left">Title</th>
                <th className="px-4 py-2 text-left">Price</th>
                <th className="px-4 py-2 text-left">Description</th>
                <th className="px-4 py-2 text-left">Category</th>
                <th className="px-4 py-2 text-left">Summary</th>
                <th className="px-4 py-2 text-left">Status</th>
                <th className="px-4 py-2 text-left">Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.length > 0 ? (
                products.map((product, index) => (
                  <tr key={product.id} className="border-t">
                    <td className="px-4 py-2">{index + 1}</td>
                    <td className="px-4 py-2">{product.title}</td>
                    <td className="px-4 py-2">${product.price}</td>
                    <td className="px-4 py-2 text-wrap max-w-5">
                      {product.description}
                    </td>
                    <td className="px-4 py-2">{product.category.name}</td>
                    <td className="px-4 py-2">{product.summary}</td>
                    <td className="px-4 py-2">{product.status}</td>
                    <td className="px-4 py-2 space-x-2">
                      <button
                        onClick={() => editProduct(product)}
                        className="px-3 py-1 text-white bg-blue-500 rounded hover:bg-blue-600"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => openDeletePopup(product.id)}
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
                    No products available
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>

        {/* Create Product Popup */}
        {showCreatePopup && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96 max-h-[80vh] overflow-y-auto">
              <h2 className="text-lg font-bold mb-4">Create Product</h2>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Title</label>
                <input
                  type="text"
                  name="title"
                  value={newProductData.title}
                  onChange={handleNewProductChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Description</label>
                <textarea
                  name="description"
                  value={newProductData.description}
                  onChange={handleNewProductChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Summary</label>
                <textarea
                  name="summary"
                  value={newProductData.summary}
                  onChange={handleNewProductChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Category</label>
                <select
                  value={newProductData.category}
                  onChange={handleCategoryChangeCreate}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="">Select a category</option>
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Price</label>
                <input
                  type="number"
                  name="price"
                  value={newProductData.price}
                  onChange={handleNewProductChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Status</label>
                <select
                  name="status"
                  value={newProductData.status}
                  onChange={handleNewProductChange}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="ACTIVE">ACTIVE</option>
                  <option value="INACTIVE">INACTIVE</option>
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
                  onClick={() => createProducts()}
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
            <div className="bg-white p-6 rounded shadow-md w-96 max-h-[80vh] overflow-y-auto">
              <h2 className="text-xl font-bold mb-4">Edit Product</h2>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Title</label>
                <input
                  type="text"
                  name="title"
                  value={editProductData.title}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Description</label>
                <textarea
                  name="description"
                  value={editProductData.description}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Category</label>
                <select
                  name="category"
                  value={editProductData.category}
                  onChange={handleCategoryChange}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  {categories.map((category) => (
                    <option key={category.id} value={category.id}>
                      {category.name}
                    </option>
                  ))}
                </select>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Status</label>
                <select
                  name="status"
                  value={editProductData.status}
                  onChange={handleStatusChange}
                  className="w-full p-2 border border-gray-300 rounded"
                >
                  <option value="ACTIVE">Active</option>
                  <option value="INACTIVE">InActive</option>
                </select>
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Price</label>
                <input
                  type="number"
                  name="price"
                  value={editProductData.price}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
              </div>
              <div className="mb-4">
                <label className="block text-gray-700 mb-2">Price</label>
                <input
                  type="text"
                  name="summary"
                  value={editProductData.summary}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded"
                />
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
                  Confirm
                </button>
              </div>
            </div>
          </div>
        )}

        {/* Delete Confirmation Popup */}
        {showDeletePopup && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-6 rounded shadow-md w-96">
              <h2 className="text-lg font-bold mb-4">Confirm Deletion</h2>
              <p className="text-gray-700 mb-4">
                Are you sure you want to delete this product? This action cannot
                be undone.
              </p>
              <div className="flex justify-end space-x-4">
                <button
                  onClick={() => setShowDeletePopup(false)}
                  className="px-4 py-2 bg-gray-300 rounded hover:bg-gray-400"
                >
                  Cancel
                </button>
                <button
                  onClick={deleteProduct}
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

export default ManageProducts;
