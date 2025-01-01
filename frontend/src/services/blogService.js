export const getBlogs = async () => {
  const response = await fetch("http://localhost:8080/blogs", {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) throw new Error("Failed to fetch blogs");

  const data = await response.json();
  console.log(data);
  return data;
};
export const getBlogById = async (id) => {
  const response = await fetch(`http://localhost:8080/blogs/${id}`, {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) throw new Error("Failed to fetch blog by id");

  const data = await response.json();
  return data;
};
