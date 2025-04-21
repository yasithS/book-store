# RESTful Bookstore API

A Java-based RESTful API for a bookstore application built using JAX-RS (Jersey). This API provides endpoints for managing books, authors, shopping carts, and orders with in-memory data storage and JSON communication.

## Features

- **Book Management**: Create, read, update, and delete books
- **Author Management**: Create, read, update, and delete authors and view their books
- **Shopping Cart**: Add books to cart, update quantities, and remove items
- **Order Processing**: Place orders and view order history

## Tech Stack

- Java 21
- JAX-RS (Jersey) for REST API implementation
- Grizzly HTTP Server
- Jackson for JSON serialization/deserialization
- Maven for dependency management

## Project Structure

```
BookstoreAPI/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── bookstore/
│                   ├── Main.java                            # Application entry point
│                   ├── AppConfig.java                       # JAX-RS configuration
│                   ├── exception/                           # Custom exceptions
│                   │   ├── AuthorNotFoundException.java
│                   │   ├── BookNotFoundException.java
│                   │   ├── CartItemNotFoundException.java
│                   │   ├── CartNotFoundException.java
│                   │   ├── EmptyCartException.java
│                   │   ├── GlobalExceptionMapper.java
│                   │   └── OrderNotFoundException.java
│                   ├── model/                                # Data models
│                   │   ├── Author.java
│                   │   ├── Book.java
│                   │   ├── CartItem.java
│                   │   └── Order.java
│                   ├── repository/                           # Data access
│                   │   ├── BookRepository.java
│                   │   ├── CartRepository.java
│                   │   └── OrderRepository.java
│                   └── resource/                             # REST resources
│                       ├── AuthorResource.java
│                       ├── BookResource.java
│                       ├── CartResource.java
│                       └── OrderResource.java
└── pom.xml                                                   # Maven configuration
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21 or newer
- Maven 3.6 or newer

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/restful-bookstore-api.git
   cd restful-bookstore-api
   ```

2. Build the project with Maven:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.bookstore.Main"
   ```

The server will start at `http://localhost:8080/api/`

## API Endpoints

### Books

- **GET** `/api/books` - Get all books
- **GET** `/api/books/{id}` - Get a specific book by ID
- **POST** `/api/books` - Create a new book
- **PUT** `/api/books/{id}` - Update a book
- **DELETE** `/api/books/{id}` - Delete a book

### Authors

- **GET** `/api/authors` - Get all authors
- **GET** `/api/authors/{id}` - Get a specific author by ID
- **POST** `/api/authors` - Create a new author
- **PUT** `/api/authors/{id}` - Update an author
- **DELETE** `/api/authors/{id}` - Delete an author
- **GET** `/api/authors/{id}/books` - Get books by a specific author

### Shopping Cart

- **GET** `/api/customers/{customerId}/cart` - Get customer's cart
- **POST** `/api/customers/{customerId}/cart` - Add item to cart
- **PUT** `/api/customers/{customerId}/cart` - Update cart item
- **DELETE** `/api/customers/{customerId}/cart/{bookId}` - Remove item from cart

### Orders

- **POST** `/api/customers/{customerId}/orders` - Place a new order
- **GET** `/api/customers/{customerId}/orders` - Get customer's order history
- **GET** `/api/customers/{customerId}/orders/{orderId}` - Get a specific order

## Example Requests

### Creating a new book

```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Great Gatsby",
    "authorId": 1,
    "isbn": "9780743273565",
    "publicationYear": 1925,
    "price": 12.99,
    "stock": 50
  }'
```

### Creating a new author

```bash
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "F. Scott Fitzgerald",
    "biography": "American novelist, essayist, and short story writer."
  }'
```

### Adding an item to cart

```bash
curl -X POST http://localhost:8080/api/customers/1/cart \
  -H "Content-Type: application/json" \
  -d '{
    "bookId": 1,
    "quantity": 2
  }'
```

### Placing an order

```bash
curl -X POST http://localhost:8080/api/customers/1/orders
```

## Error Handling

The API uses custom exceptions and a global exception mapper to provide consistent error responses:

- **404** - Resource not found (book, author, cart, cart item)
- **500** - Internal server error

Error responses are returned in JSON format:

```json
{
  "error": "Book Not Found",
  "message": "Book with ID 999 not found."
}
```

## In-Memory Storage

This implementation uses in-memory storage (Java collections) for simplicity. Data is not persisted between application restarts.

## License

This project is licensed under the GNU General Public License v3.0 - see the LICENSE file for details.

## Future Enhancements

- Add persistent database storage
- Implement user authentication and authorization
- Add more advanced search and filtering capabilities
- Implement pagination for large result sets
- Add support for book categories and tags
