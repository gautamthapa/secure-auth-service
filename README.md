# Secure Auth Service

Production-grade authentication and authorization service built using Spring Boot and PostgreSQL.

This project focuses on implementing secure JWT authentication with clean architecture and enterprise-level backend practices.

---

## 🚀 Features

* User Signup & Login
* JWT Authentication
* Spring Security Integration
* BCrypt Password Encryption
* Stateless Authentication
* Protected APIs
* Global Exception Handling
* Standard API Response Structure
* PostgreSQL Integration
* Layered Architecture
* Validation & Error Handling

---

## 🛠️ Tech Stack

* Java 21
* Spring Boot 3
* Spring Security
* PostgreSQL
* JPA / Hibernate
* JWT (Auth0 Java JWT)
* Maven

---

## 📁 Project Structure

```text
src/main/java/com/authservice

├── api
│   ├── controller
│   └── advice
│
├── application
│   ├── dto
│   └── service
│
├── infrastructure
│   ├── config
│   ├── entity
│   ├── repository
│   └── security
│
├── common
│   ├── exception
│   └── response
```

---

## 🔐 Authentication Flow

1. User signs up
2. User logs in with email/password
3. JWT access token is generated
4. Client sends JWT in Authorization header
5. Spring Security validates token
6. Protected APIs become accessible

---

## 📌 API Endpoints

### Auth APIs

| Method | Endpoint              | Description   |
| ------ | --------------------- | ------------- |
| POST   | `/api/v1/auth/signup` | Register user |
| POST   | `/api/v1/auth/login`  | Login user    |

---

## 🧪 Sample Login Request

```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```

---

## ✅ Sample Success Response

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "jwt-token"
  }
}
```

---

## ❌ Sample Error Response

```json
{
  "success": false,
  "code": 4001,
  "message": "Invalid username or password",
  "path": "/api/v1/auth/login"
}
```

---

## ⚙️ Run Locally

### Clone Repository

```bash
git clone https://github.com/your-username/secure-auth-service.git
```

---

### Configure PostgreSQL

Create database:

```sql
CREATE DATABASE secure_auth_service;
```

---

### Update application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/secure_auth_service
    username: postgres
    password: postgres
```

---

### Start Application

```bash
mvn spring-boot:run
```

---

## 🔥 Upcoming Features

* Refresh Token Support
* Refresh Token Rotation
* Session Management
* Role-Based Access Control (RBAC)
* Redis Rate Limiting
* RS256 JWT Signing
* Docker Support
* Swagger/OpenAPI Documentation

---

## 📚 Learning Goals

This project is being built to practice and demonstrate:

* Secure authentication design
* Spring Security architecture
* JWT implementation
* Enterprise backend structure
* Clean API design
* Production-level exception handling

---

## 👨‍💻 Author

Gautam
Tech Lead | Spring Boot | Distributed Systems | Cloud & Security
