import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import { Form, Button, Container, Card, Alert } from "react-bootstrap";

const API_BASE = "http://localhost:8080";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const { data } = await axios.post(`${API_BASE}/api/v1/auth/login`, form);
      console.log(data.data.token);
      localStorage.setItem("token", data.data.token);
      navigate("/products");
    } catch (err) {
      setError("Invalid login");
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: "100vh" }}>
      <Card style={{ width: "400px" }} className="p-4">
        <h3 className="mb-3 text-center">Login</h3>
        <Form onSubmit={handleSubmit}>
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
        <Form.Group className="mb-3" controlId="formBasicCheckbox">
          <Form.Check type="checkbox" label="Check me out" />
        </Form.Group>
        <Button variant="primary" type="submit">
          Login
        </Button>
        <p>
          <Form.Text className="text-muted">
            Don't have an account? <Link to="/register">Register here</Link>
          </Form.Text>
        </p>
      </Form>
      </Card>
    </Container>
  );
}