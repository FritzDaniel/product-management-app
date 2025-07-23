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

2. Navigate to the backend:
   ```bash
   cd backend

3. Build the project:
   ```bash
   ./mvnw clean install

4. Run the application:
   ```bash
   ./mvnw spring-boot:run

5. Access API / H2 Console:
   ```bash
    Base URL: http://localhost:8080/api/v1

    H2 Console: http://localhost:8080/h2-console

    JDBC URL: jdbc:h2:mem:m4a1DB | Username: root

    User Seeder (Automatically Created when run the application)
    username: admin
    password: secret

    You can access the Postman on backend/postman

### Frontend

1. Open to the the project:
   ```bash
   cd product-management-app

2. Navigate to the frontend:
   ```bash
   cd frontend

3. Install the Dependencies:
   ```bash
   npm install

4. Run the React app:
   ```bash
   npm run dev