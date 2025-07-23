import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Container, Table, Button, Form, Row, Col, Card } from "react-bootstrap";

const API_BASE = "http://localhost:8080";

function ProductsPage() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState({ id: null, name: "", description: "", price: "" });
  const [username, setUsername] = useState("");
  const [searchName, setSearchName] = useState("");
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");

  useEffect(() => {
    if (!token) navigate("/login");
    fetchProducts();
    fetchProfile();
  }, []);

  const handleSearch = async () => {
    try {
      const { data } = await axios.get(`${API_BASE}/api/v1/products/search`, {
        params: { name: searchName },
        headers: { Authorization: `Bearer ${token}` },
      });
      setProducts(data);
    } catch (err) {
      console.error("Search failed", err);
    }
  };

  const handleFilter = async () => {
    try {
      const { data } = await axios.get(`${API_BASE}/api/v1/products/filter`, {
        params: { min: minPrice, max: maxPrice },
        headers: { Authorization: `Bearer ${token}` },
      });
      setProducts(data);
    } catch (err) {
      console.error("Filter failed", err);
    }
  };

  const resetFilters = () => {
    setSearchName("");
    setMinPrice("");
    setMaxPrice("");
    fetchProducts();
  };

  const fetchProducts = async () => {
    console.log(token);
    const res = await axios.get(`${API_BASE}/api/v1/products`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    setProducts(res.data);
  };

  const fetchProfile = async () => {
    try {
      const { data } = await axios.get(`${API_BASE}/api/v1/auth/profile`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setUsername(data.data.username);
    } catch (err) {
      console.error("Profile fetch failed", err);
    }
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
    try {
      await axios.delete(`${API_BASE}/api/v1/products/delete/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchProducts(); // ✅ Refresh list after delete
    } catch (err) {
      console.error("Delete failed", err);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  useEffect(() => {
    fetchProducts();
  }, []);

  return (
    <Container className="py-5">
      <Card className="p-4 mb-4">
        <Row className="align-items-center mb-3">
          <Col>
            <h2><i className="bi bi-box"></i> Product Manager</h2>
          </Col>
          <Col className="text-end">
            {username && (
              <span className="me-3 text-muted">
                Logged in as: <strong>{username}</strong>
              </span>
            )}
            <Button variant="outline-danger" onClick={logout}>Logout</Button>
          </Col>
        </Row>

        <Row className="g-2">
          <Col md>
            <Form.Control
              placeholder="Name"
              value={form.name}
              onChange={(e) => setForm({ ...form, name: e.target.value })}
            />
          </Col>
          <Col md>
            <Form.Control
              placeholder="Description"
              value={form.description}
              onChange={(e) => setForm({ ...form, description: e.target.value })}
            />
          </Col>
          <Col md>
            <Form.Control
              placeholder="Price"
              type="number"
              value={form.price}
              onChange={(e) => setForm({ ...form, price: e.target.value })}
            />
          </Col>
          <Col md="auto">
            <Button onClick={handleSubmit}>{form.id ? "Update" : "Add"} Product</Button>
          </Col>
        </Row>
      </Card>

      <Card className="p-3">
        <h5>Product List</h5>

        <Row className="mb-3">
          <Col md={4}>
            <Form.Control
              type="text"
              placeholder="Search by Name"
              value={searchName}
              onChange={(e) => setSearchName(e.target.value)}
            />
          </Col>
          <Col md="auto">
            <Button variant="primary" onClick={handleSearch}>Search</Button>
          </Col>
          <Col md={2}>
            <Form.Control
              type="number"
              placeholder="Min Price"
              value={minPrice}
              onChange={(e) => setMinPrice(e.target.value)}
            />
          </Col>
          <Col md={2}>
            <Form.Control
              type="number"
              placeholder="Max Price"
              value={maxPrice}
              onChange={(e) => setMaxPrice(e.target.value)}
            />
          </Col>
          <Col md="auto">
            <Button variant="warning" onClick={handleFilter}>Filter</Button>
          </Col>
          <Col md="auto">
            <Button variant="secondary" onClick={resetFilters}>Reset</Button>
          </Col>
        </Row>

        <Table striped bordered hover responsive>
          <thead>
            <tr>
              <th>#</th>
              <th>Name</th>
              <th>Description</th>
              <th>Price (Rp)</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {products.length === 0 ? (
              <tr><td colSpan="5" className="text-center">No products yet.</td></tr>
            ) : (
              products.map((p, i) => (
                <tr key={p.id}>
                  <td>{i + 1}</td>
                  <td>{p.name}</td>
                  <td>{p.description}</td>
                  <td>{parseInt(p.price).toLocaleString("id-ID")}</td>
                  <td>
                    <Button variant="outline-primary" onClick={() => handleEdit(p)}>Edit</Button>
                    <Button variant="outline-danger" onClick={() => handleDelete(p.id)}>Delete</Button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </Table>
      </Card>
    </Container>
  );
}

export default ProductsPage;
