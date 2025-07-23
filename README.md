# Product Management System

A full-stack web application for managing products, built with Java Spring Boot (backend) and React + Bootstrap (frontend).  
Includes JWT authentication, full CRUD functionality, search, filtering, and caching.

---

## 🔧 Tech Stack

- **Backend**: Java 17, Spring Boot, Spring Security, JWT, H2 (in-memory DB)
- **Frontend**: React, React Router, React Bootstrap
- **Build Tool**: Maven
- **Testing**: JUnit
- **Cache**: Spring In-Memory Cache (`@Cacheable`)

---

## 🚀 Features

### 🔐 Authentication
- JWT-based login/register
- Role-based access
- Auth-protected endpoints

### 📦 Product Management
- Create, Read, Update, Delete (CRUD)
- Input validation
- Caching for product listing

### 🔍 Filtering & Search
- Filter products by price range
- Search products by name
- Fetch product by ID

### 💻 Frontend UI
- Login & register pages
- Product manager dashboard with:
  - Bootstrap-powered layout
  - Filter/search inputs
  - Live product table
  - Add product form
  - Logout button
  - Logged-in user display

---

## 🧪 Testing

- Unit tests using JUnit for service and controller layers
- Authentication tests
- Product CRUD and validation test cases

---

## 🖥️ Run Locally

### Backend

1. Clone the project:
   ```bash
   git clone https://github.com/FritzDaniel/product-management-app
   cd product-management-app