# GATEWAY-APP

## Summary

GATEWAY-APP is a full-stack application built with Spring Boot (Java) for the backend and React (JavaScript) for the frontend. It uses Keycloak as the Identity Provider (IdP) for authentication and authorization.

---

## Features

- Secure authentication and authorization with Keycloak
- RESTful API backend (Spring Boot)
- Modern frontend (React)
- Maven for backend build management
- npm for frontend package management

---

## Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+ and npm 9+
- Keycloak server (configured as IdP)

---

## Getting Started

### 1. Clone the repository

``` shell
git clone https://github.com/your-org/gateway-app.git
cd gateway-app
```

### 2. Backend Setup (Spring Boot)
Configure application.yml or application.properties with your Keycloak settings.
Build and run:

``` shell
cd backend
mvn clean install
```

### 3. Frontend Setup (React)
``` shell
cd frontend
npm install
npm start
```

### 4. Keycloak Configuration
Create a realm and client in Keycloak.
Set client authentication method (e.g., client-secret, client-jwt, none for PKCE).
Enable PKCE for public clients if using Authorization Code with PKCE.

---

## Configuration
Spring Boot Keycloak Example:
``` yaml
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: your-client-id
            client-secret: your-client-secret
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:3000/login/oauth2/code/keycloak
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/your-realm
```
React Keycloak Example

Use a library like @react-keycloak/web and configure with your Keycloak settings.

---

## Scripts:
```shell
mvn clean install - Build backend
npm install - Install frontend dependencies
npm start - Start frontend dev server
```

---

## License
MIT - Replace placeholders with your actual project details as needed.

<br/>

