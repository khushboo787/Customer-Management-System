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
git clone https://github.com/your-username/customer-management-system.git
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

### API Endpoints

#### User Endpoints

- **Add Role**: Inserts a role into the database (e.g., ROLE_ADMIN).
  - **URL**: `/addrole`
  - **Method**: `POST`
  - **Body**: JSON object with role information

- **Register User**: Registers a new user.
  - **URL**: `/register`
  - **Method**: `POST`
  - **Body**: JSON object with user details

- **Login User**: Authenticates a user.
  - **URL**: `/login`
  - **Method**: `POST`
  - **Body**: JSON object with login credentials

- **Get User**: Retrieves user details by email.
  - **URL**: `/getuser/{email}`
  - **Method**: `GET`
  - **Path Variable**: `email`

#### Customer Endpoints

- **Add Customer**: Adds a new customer (Authentication required).
  - **URL**: `/addCustomer`
  - **Method**: `POST`
  - **Body**: JSON object with customer details

- **Get All Customers**: Retrieves a list of all customers.
  - **URL**: `/getallCustomers`
  - **Method**: `GET`

- **Update Customer**: Updates customer information by ID (Authentication required).
  - **URL**: `/updateCustomer/{id}`
  - **Method**: `PUT`
  - **Path Variable**: `id`
  - **Body**: JSON object with updated customer details

- **Delete Customer**: Deletes a customer by ID (Authentication required).
  - **URL**: `/deleteCustomer/{id}`
  - **Method**: `DELETE`
  - **Path Variable**: `id`

- **Get Customer**: Retrieves a single customer's details by ID.
  - **URL**: `/getCustomer/{id}`
  - **Method**: `GET`
  - **Path Variable**: `id`

### Sync Functionality

#### Sync Button

On the customer list screen, a `Sync` button is available to fetch customer data from a remote API and update the local database. If the customer already exists, the existing record will be updated instead of inserting a new one.

#### Authentication API

- **URL**: `https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
      "login_id": "test@sunbasedata.com",
      "password": "Test@123"
  }
