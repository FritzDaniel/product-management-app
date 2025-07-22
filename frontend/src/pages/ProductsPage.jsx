import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const API_BASE = "http://localhost:8080";

function ProductsPage() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({ id: null, name: "", description: "", price: "" });

  const fetchProducts = async () => {
    console.log(token);
    const res = await axios.get(`${API_BASE}/api/v1/products`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    setProducts(res.data);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (form.id) {
        await axios.put(`${API_BASE}/api/v1/products/${form.id}`, form, {
          headers: { Authorization: `Bearer ${token}` },
        });
      } else {
        await axios.post(`${API_BASE}/api/v1/products`, form, {
          headers: { Authorization: `Bearer ${token}` },
        });
      }
      setForm({ id: null, name: "", description: "", price: "" });
      fetchProducts();
    } catch (err) {
      alert("Failed to save product");
    }
  };

  const handleEdit = (p) => {
    setForm(p);
  };

  const handleDelete = async (id) => {
    await axios.delete(`${API_BASE}/api/v1/products/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    fetchProducts();
  };

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <div className="p-4">
      <h2>📦 Product Manager</h2>
      <button onClick={logout}>Logout</button>
      <form onSubmit={handleSubmit} className="mb-4">
        <input
          placeholder="Name"
          value={form.name}
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />
        <input
          placeholder="Description"
          value={form.description}
          onChange={(e) => setForm({ ...form, description: e.target.value })}
        />
        <input
          placeholder="Price"
          type="number"
          value={form.price}
          onChange={(e) => setForm({ ...form, price: e.target.value })}
        />
        <button type="submit">{form.id ? "Update" : "Add"} Product</button>
      </form>

      <ul>
        {products.map((p) => (
          <li key={p.id}>
            {p.name} - Rp {p.price}
            <button onClick={() => handleEdit(p)}>Edit</button>
            <button onClick={() => handleDelete(p.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default ProductsPage;
