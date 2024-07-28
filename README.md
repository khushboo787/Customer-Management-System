# Customer Management System

The Customer Management System is a web application designed to manage customer information. It allows users to perform CRUD (Create, Read, Update, Delete) operations on customer data, and includes a feature to sync customer data with a remote API.

## Technologies Used

- **Backend**: Spring Boot, Java
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)

## Features

- User authentication and authorization.
- CRUD operations for customer data.
- Sync functionality to fetch and update customer data from a remote API.
- Search and pagination for customer records.

## Setup and Installation

### Prerequisites

- Java 17 or later
- MySQL
- Maven
- Git

### Clone the Repository

```
git clone https://github.com/khushboo787/customer-management-system.git
cd customer-management-system
```
#### Configure the Database

Create a database named customer_db in MySQL.
Update the application.properties file with your MySQL database credentials.
properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect

```


 ### Build and Run the Application
mvn clean install
mvn spring-boot:run
The application will be accessible at http://localhost:8070.

## API Endpoints

### User Endpoints
- **Add Role**: `POST /addrole` - Inserts a role into the database.
- **Register User**: `POST /register` - Registers a new user.
- **Login User**: `POST /login` - Authenticates a user.
- **Get User**: `GET /getuser/{email}` - Retrieves user details by email.

### Customer Endpoints
- **Add Customer**: `POST /addCustomer` - Adds a new customer (Authentication required).
- **Get All Customers**: `GET /getallCustomers` - Retrieves a list of all customers.
- **Update Customer**: `PUT /updateCustomer/{id}` - Updates customer information by ID (Authentication required).
- **Delete Customer**: `DELETE /deleteCustomer/{id}` - Deletes a customer by ID (Authentication required).
- **Get Customer**: `GET /getCustomer/{id}` - Retrieves a single customer's details by ID.

### Sync Functionality

#### Sync Button
- A `Sync` button on the customer list screen fetches customer data from a remote API and updates the local database. Existing customers will be updated instead of inserting new ones.

#### Authentication API
- **URL**: `https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "login_id": "test@sunbasedata.com",
      "password": "Test@123"
  }

```
 ## Sync API
- **URL**: `https://qa.sunbasedata.com/sunbase/portal/api/customers`
- **Method**: `GET`
- **Headers**:
  ```json
  {
      "Authorization": "Bearer <token>"
  }
```

The fetched customer data is sent to the backend API endpoint /api/syncCustomers for processing and updating the local database.

### License
This project is licensed under the MIT License. See the LICENSE file for more details.
