import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({ product }) => {
  return (
    <div className="bg-purple-50 hover:shadow-lg ease-in duration-200 text-gray-800 rounded-lg shadow-md p-6 min-w-[250px] flex-shrink-0 border border-gray-200">
      {/* Product Title */}
      <h3 className="text-lg font-semibold mb-2 text-purple-600">
        {product.title}
      </h3>
      {/* Product Description */}
      <p className="text-sm mb-3 text-gray-600">{product.description}</p>
      {/* Product Price */}
      <div className="font-medium mb-4 text-gray-800">₹ {product.price}</div>
      {/* Enroll Button */}
      <Link
        to={`/payments/${product.id}`}
        className="py-2 px-4 rounded transition actionbutton"
      >
        Enroll
      </Link>
    </div>
  );
};

export default ProductCard;
