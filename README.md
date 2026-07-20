# Task Management API

## Overview

This is a RESTful API developed with **Spring Boot**, **Java 25**, and **PostgreSQL**. 
It simulates a real-world task management API where only authenticated users can create tasks and delete their own ones safely.  
The goal of this project is to demonstrate my backend development skills and knowledge such as REST API, authentication, authorization, DB design, and security considerations.

While developing this application, I focused on:

- Layered architecture
- Spring Security
- JWT authentication
- Bean Validation
- JPA relationships
- Pagination
---
## Features

### Authentication
- JWT authentication ensures only authenticated users can create and manage their own tasks

### Task Management
- Create new tasks
- View all tasks with pagination
- View only your tasks with pagination
- View a single task with user information
- Delete my tasks

### Security Design
- Passwords are hashed using argon2 which is strong against GPU attacks
- JWT tokens are validated for each request

### User Interface
- OpenAPI / SwaggerUI Documentation

### Technical Highlights
- Using pagination to display tasks for scalability
- Clean architecture separating service and controller for maintainability
- Bean validation prevents invalid user inputs

---
## Tech Stack

### Tech | Version
- Java | 25 
- Spring Boot | 4.x 
- Spring Security | 7.x 
- Spring Data JPA | 4.x 
- PostgreSQL | 17 
- Maven | 3.x 
- JWT | JJWT 
- Password Hashing | Argon2 
- OpenAPI | springdoc-openapi 
---

## API Endpoints
### Authentication
- Login | POST /auth/login

### Users
- Create User | POST /users
- Get My Information | GET /users

### Tasks
- Create Tasks | POST /tasks
- Get Tasks | GET /tasks
- Get My Tasks | GET /tasks/me
- Get Single Task | /tasks/{taskId}
- Delete My Task | DELETE /tasks/{taskId}
---
## Database

### User
- id | UUID
- username | String
- password (hashed) | String

### Task
- id | UUID
- title | String
- description | String
- completed | boolean
- user_id | UUID

### Relationship:

```
User (1)
   │
   └──────< Task (Many)
```
---
## How to Run Locally

Clone the repository.

```
git clone https://github.com/longnight-a11y/task-management-api-Spring-Boot.git
```

Configure your database in `application.yml` which is located in src/main/resources.

Example:

```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskdb
    username: your_username
    password: your_password

app:
  jwt:
    secret: your_secret_key
```

Run the application with the command below.

```
mvn spring-boot:run
```

---

## Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```

---

## Future Improvements
- Add the updateTask endpoint
- Deployment
- Docker support
- Database Migration
- Integration Tests
- Unit Tests
- GitHub Actions CI

---
## Author

- Mirai (longnight-a11y)