# ShopFlowAPI

ShopFlowAPI is a RESTful backend application developed using **Spring Boot** to support modern e-commerce mobile applications. The API provides secure authentication, product management, user management, order processing, payment workflows, and database integration for scalable shopping platforms.

The project was built to strengthen practical experience in **Spring Boot**, **REST API development**, **JWT authentication**, **database management**, and **full-stack application architecture**. It serves as the backend system for mobile applications developed with Flutter.

---

# Features

### Authentication & Security
- User registration and login
- JWT Authentication & Authorization
- Protected API endpoints
- Secure access control using Spring Security

### Product Management
- Create products
- Update products
- Delete products
- Product search & filtering
- Product image upload
- Category management

### User Features
- User profile management
- Change password
- Upload profile image
- User information retrieval

### Order & Payment
- Shopping cart integration
- Order creation and processing
- Checkout workflow
- Payment verification support

### Backend Features
- RESTful API architecture
- File upload system
- Exception handling
- Validation handling
- Layered architecture
- Database integration

---

# Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate / JPA
- Maven
- MySQL
- Swagger / OpenAPI
- Lombok
- Git & GitHub

---

# Architecture

Project structure follows layered architecture:

```txt
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

Folders:

```txt
controller/
service/
repository/
entity/
dto/
config/
security/
utils/
```

---

# Main Functionalities

✔ Authentication System  
✔ JWT Security  
✔ Product CRUD  
✔ Category Management  
✔ User Management  
✔ Order Processing  
✔ File Upload System  
✔ REST APIs  
✔ Payment Support  
✔ MySQL Integration  

---

# Example API Endpoints

Authentication:

```http
POST /api/auth/register
POST /api/auth/login
POST /api/auth/verify-otp
```

Products:

```http
GET  /api/product
POST /api/product/create
PUT  /api/product/{id}
DELETE /api/product/{id}
```

Users:

```http
GET /api/user/me
PUT /api/user/set_profile
PATCH /api/user/change-password
```

Orders:

```http
POST /api/order/create
GET  /api/order
```

---

# Installation

Clone repository:

```bash
git clone https://github.com/HoeunRaksa/ShopFlowAPI.git
```

Move into project:

```bash
cd ShopFlowAPI
```

Install dependencies:

```bash
mvn clean install
```

Run application:

```bash
mvn spring-boot:run
```

---

# Database Configuration

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ShopFlowDB
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

---

# Future Improvements

Planned features:

- Refresh Token Authentication
- Notification System
- Admin Dashboard
- Real Payment Gateway
- Product Reviews
- Wishlist
- Multi-language Support

---

# Author

**Hoeun Raksa**

Full Stack Developer  
Flutter • Spring Boot • ReactJS • Laravel

GitHub:
https://github.com/HoeunRaksa

---

# License

Developed for learning purposes, portfolio projects, and full-stack development practice.
