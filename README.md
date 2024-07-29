# Customer Management System

The Customer Management System is a web application designed to manage customer information. It allows users to perform CRUD (Create, Read, Update, Delete) operations on customer data, and includes a feature to sync customer data with a remote API.

## Technologies Used

- **Backend**: Core Java, Spring Boot, JSP, Spring Security
- **Frontend**:Thymeleaf, HTML, CSS, JavaScript
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)


## Features

- **Authentication and Authorization:** Secure user login and role-based access control using Spring Security.
- **CRUD Operations:** Create, read, update, and delete customer records.
- **Search Functionality** : Search and pagination for customer records.
- **Data Synchronization:** Fetch and update customer data from a remote API.
- **Frontend:** User-friendly interface built with  hymeleaf, HTML, CSS, and JavaScript.


## Setup and Installation

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

#### The application will be accessible at http://localhost:8070

### Build and Run the Application
```
mvn clean install
mvn spring-boot:run
```


## API Endpoints

### User Endpoints
- **Add Role**: `POST /addrole` -(here need to insert the role into database before register like: ROLE_ADMIN)
- **Register User**: `POST /register` - Registers a new user.
- **Login User**: `POST /login` - Authenticates a user.
- **Get User**: `GET /getuser/{email}` - Retrieves user details by email.
 
 ### Login Endpoint
       **Endpoint:** `POST /login`
       **URL:** `http://localhost:8070/login`


#### Request Body

```json
{
  "email": "seema@gmail.com",
  "password": "123456"
}

```


#### Response
```
      {
          "message": "Login successful!",
          "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzZWVtYUBnbWFpbC5jb20iLCJpYXQiOjE3MjIyMzQyMDYsImV4cCI6MTcyMjMyMDYwNn0.T-x_DQ7FecqC5kjMG6Xl5z8nd-2ShSr1L9kfoSg6VnMgEre4I3nPEck73HvUK45KJk9-kre6_Zg02oHno_mt_w"
     }
```


### Customer Endpoints

- **Add Customer:** `POST /addCustomer`
- **Get All Customers:** `GET /getallCustomers`
- **Update Customer:** `PUT /updateCustomer/{id}`
- **Delete Customer:** `DELETE /deleteCustomer/{id}`
- **Get Customer by ID:** `GET /getCustomer/{id}`
- **Sync Button** :  A `Sync` button on the customer list screen fetches customer data from a remote API and updates the local database.
   

### License
This project is licensed under the MIT License. See the LICENSE file for more details.
