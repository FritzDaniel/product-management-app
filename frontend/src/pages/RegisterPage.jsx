import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { Form, Button, Container, Card, Alert } from "react-bootstrap";

const API_BASE = "http://localhost:8080";

function RegisterPage() {
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [form, setForm] = useState({ username: "", password: "" });

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post(`${API_BASE}/api/v1/auth/register`, form);
      localStorage.setItem("token", res.data.data.token);
      alert("Registration successful!");
      navigate("/products");
    } catch (err) {
      setError("Registration failed: " + err.response?.data?.message || err.message);
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "100vh" }}>
      <Card style={{ width: "400px" }} className="p-4">
        <h3 className="mb-3 text-center">Register</h3>
        <Form onSubmit={handleRegister}>
        {error && <Alert variant="danger">{error}</Alert>}
        <Form.Group className="mb-3" controlId="formBasicEmail">
          <Form.Label>Username</Form.Label>
          <Form.Control 
            type="text"
            required
            value={form.username}
            onChange={(e) => setForm({ ...form, username: e.target.value })}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control 
            type="password"
            required
            value={form.password}
            onChange={(e) => setForm({ ...form, password: e.target.value })} />
        </Form.Group>

        <Button variant="primary" type="submit">
          Register
        </Button>
        <p>
          <Form.Text className="text-muted">
            Have an account? <Link to="/login">Login here</Link>
          </Form.Text>
        </p>
      </Form>
      </Card>
    </Container>
  );
}

export default RegisterPage;