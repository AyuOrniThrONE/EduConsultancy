import React, { useState, useEffect } from "react";
import Navbar from "../components/Navbar";
import { getProducts } from "../services/productService";
import ProductCard from "../components/ProductCard";

export default function Products() {
  const [products, setProducts] = useState([]);
  const fetchProducts = async () => {
    const res = await getProducts();
    setProducts(res.courses);
  };

  useEffect(() => {
    fetchProducts();
    return () => {
      setProducts([]);
    };
  }, []);

  return (
    <>
      <Navbar />

      <section id="products" className="py-16">
        <div className="container mx-auto px-4 text-center">
          <h2 className="text-4xl font-bold  mb-6 font-cinzel">Explore</h2>
          <p className="text-lg  mb-12">
            Explore our range of services designed to guide you on your
            educational and professional journey.
          </p>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            {products.map((product, index) => (
              <ProductCard key={index} product={product} />
            ))}
          </div>
        </div>
      </section>
    </>
  );
}
