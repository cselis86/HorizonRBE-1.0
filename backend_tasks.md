# Backend App (HorizonRent) - JWT Authentication

This document outlines the tasks performed to implement JWT-based authentication in the `HorizonRent` backend application.

## 1. Update Security Configuration (`SecurityConfig.java`) - **COMPLETED**

Modified `src/main/java/org/elis/horizon/horizonrent/config/SecurityConfig.java` to:

*   **Enable JWT Authentication:** Configured the security to use JWT for authentication and authorization.
*   **CORS Configuration:** CORS configuration is now handled in `src/main/java/org/elis/horizon/horizonrent/config/CorsConfig.java`.
*   **CSRF Configuration:** Disabled CSRF.
*   **Stateless Session Management:** Configured session management to be stateless.
*   **Test Profile Configuration:** Added a specific security configuration for the `test` profile to allow unauthenticated access to `/api/properties/**` for integration testing.

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

## 8. Global Exception Handling - **COMPLETED**

*   Implemented a `GlobalExceptionHandler` to provide consistent JSON error responses for exceptions.
*   Added a custom `ErrorResponse` class for structured error messages.
*   Added unit and integration tests to verify the exception handling for `InvalidParameterException` and `PropertyNotFoundException`.

## Next Steps

*   **Implement Refresh Tokens:** Add support for refresh tokens to allow users to stay logged in for longer periods without having to re-enter their credentials.
*   **Database Integration:** Replace the in-memory user storage with a database to persist user data.
*   **Role-Based Authorization:** Enhance the authorization logic to support different user roles and permissions.

# Backend Tasks for Property Listing Feature

## TICKET-033: Create Property Model and In-Memory Repository - **COMPLETED**
**Description:** Create the Property model class and implement an in-memory repository for storing and managing property listings.
**Tasks:**
- Created Property model class with all required fields
- Created Address, Amenity, and Image models
- Implemented InMemoryPropertyRepository using ConcurrentHashMap with sample data
- Implemented CRUD operations for properties
- Implemented search and filter methods
- Ensured thread-safety for concurrent access
- Wrote unit tests for repository

## TICKET-034: Create Property DTOs - **COMPLETED**
**Description:** Create Data Transfer Objects for property-related API requests and responses.
**Tasks:**
- Created PropertyListResponse DTO (summary view)
- Created PropertyDetailResponse DTO (full details)
- Created PropertySearchRequest DTO
- Added validation annotations
- Added Lombok annotations for getters, setters, and constructors
- Created mapper utility to convert between model and DTOs (PropertyMapper)

## TICKET-035: Create Property Service Layer - **COMPLETED**
**Description:** Implement business logic for property management including search, filter, and retrieval operations.
**Tasks:**
- Created PropertyService interface and PropertyServiceImpl class
- Implemented getAllProperties method with pagination and sorting
- Implemented getPropertyById method
- Implemented searchProperties method
- Implemented getFeaturedProperties method
- Handled exceptions appropriately
- Wrote unit tests with mocked repository

## TICKET-036: Create Property REST API Endpoints - **COMPLETED** ✓
**Description:** Implement REST controllers for property listing and search functionality.
**Tasks:**
- Created PropertyController class
- Implemented GET /api/properties endpoint (list all with pagination)
- Implemented GET /api/properties/{id} endpoint (single property)
- Implemented POST /api/properties/search endpoint (search)
- Implemented GET /api/properties/featured endpoint (featured listings)
- Added request validation
- Added API documentation with Swagger
- Configured CORS

## TICKET-037: Seed Property Data for Development - **PARTIALLY COMPLETED**
**Description:** Create seed data with realistic property listings for development and testing.
**Tasks:**
- InMemoryPropertyRepository is pre-populated with sample data in its constructor.
- Further seeding for development profiles can be implemented in a dedicated seeder class.

## TICKET-044: Write Integration Tests for Property Feature (Backend part) - **COMPLETED** ✓
**Description:** Comprehensive integration tests for the complete property listing feature.
**Tasks:**
- Wrote backend integration tests for all property endpoints
- Tested complete search flow with various criteria
- Tested pagination edge cases
- Tested property not found scenarios
- Verified API contracts

## TICKET-045: Create Property Documentation (Backend part) - **PARTIALLY COMPLETED**
**Description:** Document the property listing feature for developers and users.
**Tasks:**
- Updated Swagger/OpenAPI documentation for property endpoints.
- Further documentation for data model, API examples, and user guides is pending.
