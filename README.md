# Ahmed_Hamdaoui_d54sdfsdfs8s4
This is a Spring Boot application that provides CRUD (Create, Read, Update, Delete) operations for the Class, Student and Teacher entities. The application also includes a filter on studyClassName and teacherFullName attributes with pagination, as well as JWT security on all endpoints.
## Requirements
* Java11
* Docker or PostgresQL server
## Installation
To install the application, follow these steps:

1. Clone this repository to your local machine.
2. Navigate to the root folder of the project.
3. Run the following command to start the database

`docker-compose -f src/main/docker/postgresql.yml up`

4. Once the database is up and running, open a new terminal window and navigate to the root folder of the project.
5. Run the following command to start the application:
`./mvnw spring-boot:run`

## Endpoints
* */api/register*
`{
    "username": "username",
    "password": "pass123"
}`
* */api/authenticate*
`{
    "login": "username",
    "password": "pass123"
}`
* */api/study-classes* **POST** **GET** */api/study-classes/{id}* **PUT** **PATCH** **DELETE**
* */api/teachers* **POST** **GET** */api/teachers/{id}* **PUT** **PATCH** **DELETE**
* */api/students* **POST** **GET** */api/students/{id}* **PUT** **PATCH** **DELETE**

## JWT Security
All domain endpoint are secured with valid JWT

## Resources
google, chatgpt
