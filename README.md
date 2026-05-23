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
- Subscription upgrade with payment flow

### Order & Payment
- Shopping cart integration
- Order creation and processing
- Checkout workflow
- Payment verification support
- Payment gateway callback handling
- Secure payment history (no sensitive card data stored)

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
PATCH /api/user/upgrade-subscription
PATCH /api/user/upgrade-plan
```

### Upgrade Plan (Direct)

```http
PATCH /api/user/upgrade-plan
```

**Request Body:**
```json
{
    "id": 1
}
```

**Plan Id:**
- 1 = FREE
- 2 = PREMIUM
- 3 = ULTIMATE

**Success Response:**
```json
{
    "code": 1000,
    "status": 200,
    "message": "Plan upgraded successfully",
    "data": {
        "userId": 1,
        "plan": "PREMIUM",
        "planName": "Premium Member",
        "startDate": "2026-05-23T20:10:00",
        "endDate": "2026-06-23T20:10:00",
        "active": true
    }
}
```

**Error Response:**
```json
{
    "code": 4001,
    "status": -1,
    "message": "Plan id is required",
    "data": null
}
```

Payments:

```http
POST /api/payment/callback
```

Orders:

```http
POST /api/order/create
GET  /api/order
```

---

# Subscription Upgrade Flow

The application implements a secure, asynchronous payment-based subscription upgrade flow:

1. **Request Upgrade**: User sends a `PATCH` request to `/api/user/upgrade-subscription` with the desired `subscriptionId` and card details.
2. **Payment Creation**: The backend creates a record in the `payment` table with status `PENDING`. **Sensitive card data (number, CVV) is never stored in the database.**
3. **Gateway Verification**: The payment information is (simulated) sent to the payment gateway.
4. **Gateway Callback**: The payment gateway notifies the backend via `POST /api/payment/callback`.
5. **Fulfillment**: 
   - If the status is `PAID`, the backend marks the payment as `PAID` and updates the user's `subscriptionPlan`, `startDate`, and `endDate`.
   - If the status is `FAILED`, the payment is marked as `FAILED` and no changes are made to the user's subscription.

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
