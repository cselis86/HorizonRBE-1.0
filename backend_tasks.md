# Backend App (HorizonRent) - JWT Authentication

This document outlines the tasks performed to implement JWT-based authentication in the `HorizonRent` backend application.

## 1. Update Security Configuration (`SecurityConfig.java`) - **COMPLETED**

Modified `src/main/java/org/elis/horizon/horizonrent/config/SecurityConfig.java` to:

*   **Enable JWT Authentication:** Configured the security to use JWT for authentication and authorization.
*   **CORS Configuration:** Added CORS headers to allow requests from the Vaadin frontend (e.g., `http://localhost:8081`).
*   **CSRF Configuration:** Disabled CSRF.
*   **Stateless Session Management:** Configured session management to be stateless.

## 2. Remove `login.html` - **COMPLETED**

Deleted the `src/main/resources/templates/login.html` file.

## 3. Integrate Vaadin Frontend (Deployment Strategy) - **PENDING (User Action Required)**

Determine and implement the deployment strategy for the `horizon-rent-ui` Vaadin application:

*   **Option A: Embedded within Spring Boot:** If the Vaadin app is to be served as part of the Spring Boot application, ensure the Vaadin build process places its static assets in a location accessible by Spring Boot (e.g., `src/main/resources/static`).
*   **Option B: Separate Service:** If the Vaadin app runs as a separate service, ensure proper routing and communication between the two applications. (This is the assumed current setup for development, with frontend on `8081` and backend on `8080`).

## 4. Testing - **COMPLETED**

*   Verified successful authentication and token generation using `curl`.
*   Added a unit test to verify that protected endpoints are secured.

## 5. Cleanup - **COMPLETED**

*   Removed unused dependencies and configurations related to the old `login.html`.

## 6. Gmail Login Integration - **REMOVED**

*   Removed the `spring-boot-starter-oauth2-client` dependency from `pom.xml`.
*   Removed Google OAuth2 configuration from `application.properties`.
*   Deleted `CustomOAuth2UserService.java`.
*   Updated `SecurityConfig.java` to remove OAuth2 configuration.
*   Updated `UserController.java` to remove OAuth2 handling.

## 7. User Management - **COMPLETED**

*   Implemented in-memory user storage for JWT authentication.

## Next Steps

*   **Implement Refresh Tokens:** Add support for refresh tokens to allow users to stay logged in for longer periods without having to re-enter their credentials.
*   **Database Integration:** Replace the in-memory user storage with a database to persist user data.
*   **Role-Based Authorization:** Enhance the authorization logic to support different user roles and permissions.
*   **Error Handling:** Improve error handling and provide more specific error messages to the frontend.
