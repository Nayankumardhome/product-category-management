# Category-Product CRUD Application

This is a Spring Boot-based REST API for managing categories and products. The project allows users to create, update, delete, and retrieve product and category information, ensuring proper relationships between them.

## 🚀 Features

- CRUD operations for **Category** and **Product**.
- File upload functionality for product images.
- Automatic price calculation after applying discounts.
- Proper handling of circular dependencies using `@JsonManagedReference` and `@JsonBackReference`.
- Error handling and validation.

## 🛠️ Technologies Used

- **Spring Boot** - Backend framework
- **Hibernate & JPA** - ORM for database interaction
- **MySQL** - Database for storing product and category details
- **Postman** - API testing
- **Maven** - Dependency management

## 📌 API Endpoints

### Category APIs

-------------------------------------------------------------------
| Method | Endpoint                     | Description             |
|--------|------------------------------|-------------------------|
| POST   | `/api/categories`            | Create a new category   |
| GET    | `/api/categories`            | Get all categories      |
| GET    | `/api/categories/{id}`       | Get a category by ID    |
| PUT    | `/api/categories/{id}`       | Update category details |
| DELETE | `/api/categories/{id}`       | Delete a category       |
| PATCH  | `/api/categories/{id}/image` | Update product image    |
-------------------------------------------------------------------

### Product APIs

-------------------------------------------------------------------
| Method | Endpoint                   | Description               |
|--------|----------------------------|---------------------------|
| POST   | `/api/products`            | Create a new product      |
| GET    | `/api/products`            | Get all products          |
| GET    | `/api/products/{id}`       | Get a product by ID       |
| PUT    | `/api/products/{id}`       | Update product details    |
| DELETE | `/api/products/{id}`       | Delete a product          |
| PATCH  | `/api/products/{id}/image` | Update product image      |
-------------------------------------------------------------------

## 🖼️ How to Run

1. Clone the repository:
   ```sh
   git clone https://github.com/Nayankumardhome/product-category-management.git
   cd product-category-management
