import { getUser } from "./authService";




export const getProducts = async () => {
  const response = await fetch("http://localhost:8080/courses", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) throw new Error("Failed to fetch products");

  const data = await response.json();
  return data;
};

export const getFullProducts = async () => {
  const token = localStorage.getItem("token");
  const response = await fetch("http://localhost:8080/admin/courses", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) throw new Error("Failed to fetch products");

  const data = await response.json();
  return data;
};

export const updateProduct = async (productData) => {
  const reqData = {
    title: productData.title,
    description: productData.description,
    summary: productData.summary,
    price: productData.price,
    status: productData.status,
    categoryId: productData.categoryId,
  };
  const token = localStorage.getItem("token");
  const response = await fetch(
    `http://localhost:8080/admin/courses/${productData.id}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(reqData),
    }
  );

  if (!response.ok) throw new Error("Failed to update product");

  const data = await response.json();
  return data;
};

export const createProduct = async (productData) => {
  const reqData = {
    title: productData.title,
    description: productData.description,
    summary: productData.summary,
    price: productData.price,
    status: productData.status,
    categoryId: productData.category,
  };
  const token = localStorage.getItem("token");
  const response = await fetch(`http://localhost:8080/admin/courses`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(reqData),
  });
  if (!response.ok) throw new Error("Failed to create product");

  const data = await response.json();
  return data;
};

export const deleteProductById = async (id) => {
  const token = localStorage.getItem("token");
  const response = await fetch(`http://localhost:8080/admin/courses/${id}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });
  if (!response.ok) throw new Error("Failed to Delete product");

  const data = await response.json();
  return data;
};

export const getCategories = async () => {
  const response = await fetch("http://localhost:8080/categories", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });

  if (!response.ok) throw new Error("Failed to fetch Categories");

  const data = await response.json();
  return data;
};
