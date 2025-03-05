# My Learning Project

## Technologies / Frameworks
- Java 21
- Maven
- Spring Boot
- Spring Data JDBC
- Spring Boot Test
- JUnit
- Docker
- PostgresSQL
- REST API
- Lombok
- MapStruct
- Swagger


## Links to access API
- Homepage: http://localhost:8080/api/project/homepage
- Swagger UI: http://localhost:8080/swagger-ui.html
- Swagger API: http://localhost:8080/v3/api-docs


## How to run the project
- On docker-compose file run service 'postgres_db' to start the database
- Run ProjectApplication.java to start the application


## Concepts

#### -> Use of Beans
- @Bean -> instance of a class with metadata around it (manageable by Spring)


#### -> Spring MVC
- Model -> Run class
- View -> Rest API - JSON returned as data
- Controller -> Receive request, delegate to services and return a response (dont put logic here)


#### -> @PathVariable vs @PathParam
- @PathParam is from JAX-RS, so it can only be used on REST
- @PathVariable is from Spring, so it works on both REST and MVC




