# Flower Spring API

This is a Spring Boot 2.7.18 REST API for managing flowers, built to be compatible with Java 8.

## Features
- **CRUD Operations**: Manage flowers (name, species, color, price, stock).
- **Search**: Search flowers by name or filter by color.
- **H2 Database**: Uses an in-memory database for quick start.
- **Data Initialization**: Automatically seeds the database with sample flowers.

## Project Structure
- `com.flower.spring.model`: Entity definitions.
- `com.flower.spring.repository`: JPA repositories.
- `com.flower.spring.service`: Business logic.
- `com.flower.spring.controller`: REST endpoints.

## API Endpoints
- `GET /api/flowers`: Get all flowers (supports `?name=...` search).
- `GET /api/flowers/{id}`: Get flower by ID.
- `GET /api/flowers/color/{color}`: Filter flowers by color.
- `POST /api/flowers`: Add a new flower.
- `PUT /api/flowers/{id}`: Update flower info.
- `DELETE /api/flowers/{id}`: Remove a flower.

## How to Run
1. Open this project in **IntelliJ IDEA**, **Eclipse**, or **VS Code**.
2. Ensure you have **Java 8** or higher installed.
3. Run the `FlowerSpringApplication` main method.
4. Access the API at `http://localhost:8080/api/flowers`.
5. H2 Console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:flowerdb`, Username: `sa`, Password: empty).

## Dependencies
- Spring Web
- Spring Data JPA
- H2 Database
- Project Lombok
