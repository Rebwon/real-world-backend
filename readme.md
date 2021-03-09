## real world backend app

![logo](example-logo.png)

![travis](https://travis-ci.com/Rebwon/real-world-backend.svg?branch=master)

> Spring boot + JPA codebase containing real world examples (crud, auth, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld/tree/master/api) spec and API.

### How it works

The application uses spring boot (web, jpa, querydsl)

### Security

Integration with Spring Security and add other filter for jwt token process.

The secret key is stored in `application.properties.`

### Database

spring profile dev using mysql5.7, test using embedded h2 database.

### Getting Started

You need Java 11 installed.

### Run Tests

```
/mvn clean test
```