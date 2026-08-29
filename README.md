# Local Service Booking Platform

A real-life local service booking platform built using **Java, Spring Boot, PostgreSQL, and React**.

The goal of this project is to connect customers with local service providers such as electricians, plumbers, AC repair technicians, cleaners, and other service professionals.

The project is intentionally being developed step-by-step as a relatively small but realistic full-stack application.

---

## 1. Project Overview

### Problem

Customers often need local services but may not know:

- Which provider to contact
- What services a provider offers
- How much a service costs
- How to book a service
- How to track a booking

Service providers also need a simple platform where they can:

- Register themselves
- Create their services
- Manage service information
- Receive customer bookings
- Manage booking status

### Solution

Local Service Booking Platform provides a centralized website where:

```text
Customer
   |
   | Browse Services
   | View Service Details
   | Book Service
   v
Local Service Booking Platform
   ^
   | Manage Services
   | Manage Bookings
   |
Provider
```

---

# 2. Project Goals

The main goals of this project are:

- Build a real-life business application
- Use Java + Spring Boot for the backend
- Use React for the frontend
- Use PostgreSQL as the database
- Implement JWT authentication
- Implement role-based authorization
- Build provider service management
- Build customer booking functionality
- Keep the project relatively small and practical
- Make the project suitable for a resume/portfolio
- Eventually deploy the application

---

# 3. Technology Stack

## Backend

- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- Bean Validation
- Lombok
- PostgreSQL
- Maven

> Note: The project was initially considered with Java 8, but the current backend is running on Java 17 because Spring Boot 3.x requires Java 17 or newer.

## Frontend

Planned:

- React
- Vite
- JavaScript
- HTML
- CSS
- Fetch API / Axios

## Database

- PostgreSQL

## Development Tools

- IntelliJ IDEA
- Postman
- Git
- GitHub
- PostgreSQL / psql

---

# 4. Project Structure

Current/final target structure:

```text
local-service-booking/
│
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── localfix/
│   │   │   │           ├── config/
│   │   │   │           ├── controller/
│   │   │   │           ├── dto/
│   │   │   │           ├── entity/
│   │   │   │           ├── repository/
│   │   │   │           ├── security/
│   │   │   │           └── service/
│   │   │   │
│   │   │   └── resources/
│   │   │
│   │   └── test/
│   │
│   └── pom.xml
│
├── frontend/
│   └── (React application - planned)
│
├── .gitignore
└── README.md
```

---

# 5. Application Roles

The application has three roles:

```text
CUSTOMER
PROVIDER
ADMIN
```

## CUSTOMER

A customer can:

- Register
- Login
- View own profile
- Browse services
- View service details
- Book services (planned)
- View bookings (planned)
- Cancel bookings (planned)
- Give ratings/reviews (planned)

## PROVIDER

A provider can:

- Register
- Login
- View own profile
- Create services
- View own services
- Update own services
- Delete own services
- Manage bookings (planned)
- Update booking status (planned)

## ADMIN

Admin functionality is planned for later phases.

Expected responsibilities:

- Manage users
- Manage providers
- Manage services
- Monitor bookings
- Manage/report platform data

---

# 6. Development Progress

## Phase 1 — Project Setup

### Step 1–14

Initial Spring Boot backend project was created.

Project name:

```text
local-service-booking
```

Backend package:

```text
com.localfix
```

Main application:

```text
LocalServiceBookingApplication
```

Initial backend setup included:

- Spring Boot project
- Maven configuration
- Java configuration
- Basic application class
- Basic test endpoint
- `.gitignore`
- Git repository setup
- GitHub repository setup

---

# 7. Root README Setup

The README is intentionally located at the root level:

```text
local-service-booking/README.md
```

and NOT inside:

```text
local-service-booking/backend/
```

This README documents the complete project as both backend and frontend are part of the same repository.

---

# 8. Backend Configuration

The backend uses:

```text
Spring Boot
Spring Web
Spring Data JPA
Spring Security
PostgreSQL
Bean Validation
Lombok
```

The application runs locally on:

```text
http://localhost:8080
```

Base API path:

```text
/api/v1
```

---

# 9. PostgreSQL Database Setup

Database name:

```text
local_service_booking
```

PostgreSQL database was created using:

```sql
CREATE DATABASE local_service_booking;
```

The application connects to:

```text
jdbc:postgresql://localhost:5432/local_service_booking
```

The database is used to store:

- Users
- Services
- Future bookings
- Future reviews
- Other application data

---

# 10. Database Users

The local PostgreSQL setup uses the available PostgreSQL user rather than assuming a `postgres` role exists.

Current local PostgreSQL user:

```text
amit.kumar
```

The database owner was configured accordingly.

---

# 11. JPA Configuration

Current development configuration uses:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

This allows Hibernate to automatically create/update tables during development.

For production, database migration management such as Flyway should be preferred.

---

# 12. Initial Test API

A basic test endpoint was created to verify that the Spring Boot backend is running.

Example:

```text
GET /api/v1/test
```

Expected response:

```text
Local Service Booking Backend is running!
```

This endpoint was used to confirm that the backend was successfully started.

---

# 13. Authentication System

## Phase 2/3 — Authentication

The application implements authentication using:

```text
Email + Password
        |
        v
Spring Security
        |
        v
BCrypt Password Hashing
        |
        v
JWT Token
```

---

# 14. User Registration

Registration API:

```text
POST /api/v1/auth/register
```

Example request:

```json
{
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "password": "password123",
  "role": "CUSTOMER"
}
```

The password is not stored as plain text.

It is encoded using BCrypt.

---

# 15. User Login

Login API:

```text
POST /api/v1/auth/login
```

Example request:

```json
{
  "email": "amit@example.com",
  "password": "password123"
}
```

Successful login returns:

```json
{
  "id": 1,
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "role": "CUSTOMER",
  "token": "JWT_TOKEN"
}
```

---

# 16. BCrypt Password Hashing

Passwords are protected using:

```text
BCryptPasswordEncoder
```

The database stores a BCrypt hash instead of the original password.

Example database value:

```text
$2a$10$...
```

---

# 17. JWT Authentication

After successful login, the backend generates a JWT.

JWT contains information such as:

```text
subject/email
userId
name
role
issued time
expiration time
```

The client sends the JWT with protected requests:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 18. JWT Filter

A custom:

```text
JwtAuthenticationFilter
```

was implemented.

Its responsibility is:

```text
Request
   |
   v
Read Authorization header
   |
   v
Extract Bearer token
   |
   v
Validate JWT
   |
   v
Load user/authentication information
   |
   v
Set SecurityContext
   |
   v
Controller
```

Invalid or missing tokens are rejected for protected APIs.

---

# 19. Stateless Security

Spring Security uses:

```text
SessionCreationPolicy.STATELESS
```

The application therefore does not depend on server-side HTTP sessions for API authentication.

Authentication is handled through JWT.

---

# 20. Protected User API

User profile API:

```text
GET /api/v1/users/me
```

Header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example response:

```json
{
  "id": 1,
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "role": "CUSTOMER",
  "createdAt": "2026-08-18T08:26:05.958026",
  "updatedAt": null
}
```

The API uses a response DTO instead of exposing the password to the client.

---

# 21. Role-Based Authorization

Spring Security method-level authorization was enabled.

Example:

```java
@PreAuthorize("hasRole('PROVIDER')")
```

This ensures that only users with the required role can access provider APIs.

---

# 22. Role Security Model

```text
CUSTOMER
   |
   └── Customer APIs

PROVIDER
   |
   └── Provider APIs

ADMIN
   |
   └── Admin APIs (planned)
```

A CUSTOMER cannot access PROVIDER-only endpoints.

A PROVIDER cannot access ADMIN-only endpoints.

---

# 23. Provider Test API

A provider test endpoint was created:

```text
GET /api/v1/provider/test
```

It is protected using:

```java
@PreAuthorize("hasRole('PROVIDER')")
```

Expected successful response:

```text
Provider API is working!
```

---

# 24. Service Management

## Phase 6/7 — Service Management

The first major business feature of the application is service management.

Business flow:

```text
Provider
   |
   v
Create Service
   |
   v
PostgreSQL
   |
   v
Customer
   |
   v
Browse Service
   |
   v
View Service Details
```

---

# 25. Service Entity

The `Service` entity represents a service offered by a provider.

Main fields:

```text
id
title
description
category
price
provider
createdAt
updatedAt
```

Database table:

```text
services
```

---

# 26. Service and Provider Relationship

Relationship:

```text
User (PROVIDER)
       |
       | 1
       |
       | *
       v
    Service
```

One provider can create multiple services.

Example:

```text
Raj Provider
    |
    ├── AC Repair
    ├── Plumbing
    └── Electrical Repair
```

Each service belongs to exactly one provider.

---

# 27. Service Repository

The application uses:

```text
ServiceRepository
```

which extends:

```java
JpaRepository<Service, Long>
```

It supports database operations such as:

- Save service
- Find service
- Find all services
- Delete service
- Find services by provider

---

# 28. Create Service DTO

Provider service creation uses:

```text
CreateServiceRequest
```

Fields:

```text
title
description
category
price
```

Validation is applied using Jakarta Bean Validation.

Examples:

```text
Title is required
Description is required
Category is required
Price must be greater than 0
```

---

# 29. Service Response DTO

The API returns:

```text
ServiceResponse
```

instead of directly returning the entity.

Response contains:

```text
id
title
description
category
price
providerId
providerName
createdAt
updatedAt
```

This prevents unnecessary user/entity data from being exposed.

---

# 30. Provider — Create Service

API:

```text
POST /api/v1/provider/services
```

Authorization:

```text
PROVIDER
```

Example request:

```json
{
  "title": "AC Repair",
  "description": "Professional AC repair and servicing at your doorstep.",
  "category": "AC Repair",
  "price": 499
}
```

The provider is determined from the authenticated JWT/security context.

The client does not need to send `providerId`.

---

# 31. Provider — View Own Services

API:

```text
GET /api/v1/provider/services/my
```

Authorization:

```text
PROVIDER
```

This returns only the services belonging to the currently authenticated provider.

---

# 32. Browse All Services

API:

```text
GET /api/v1/services
```

This returns available services.

Example:

```json
[
  {
    "id": 1,
    "title": "AC Repair",
    "description": "Professional AC repair and servicing at your doorstep.",
    "category": "AC Repair",
    "price": 499,
    "providerId": 2,
    "providerName": "Raj Provider"
  }
]
```

---

# 33. View Single Service

API:

```text
GET /api/v1/services/{serviceId}
```

Example:

```text
GET /api/v1/services/1
```

This returns details of one service.

---

# 34. Update Service

Providers can update their own services.

API:

```text
PUT /api/v1/provider/services/{serviceId}
```

Example:

```json
{
  "title": "Premium AC Repair",
  "description": "Professional AC repair and deep servicing at your doorstep.",
  "category": "AC Repair",
  "price": 699
}
```

Only the provider who owns the service should be allowed to update it.

---

# 35. Delete Service

Providers can delete their own services.

API:

```text
DELETE /api/v1/provider/services/{serviceId}
```

Expected successful response:

```text
204 No Content
```

---

# 36. Provider Ownership Security

Service ownership is checked in the business layer.

Example:

```text
Provider A
   |
   └── Service 1

Provider B
   |
   └── Service 2
```

Provider B must not be able to:

```text
❌ Update Service 1
❌ Delete Service 1
```

The backend compares:

```text
service.provider.id
        vs
authenticated provider.id
```

Only the owner can update/delete the service.

---

# 37. Current API Summary

## Authentication

```text
POST /api/v1/auth/register
POST /api/v1/auth/login
```

## User

```text
GET /api/v1/users/me
```

## Provider

```text
GET    /api/v1/provider/test
POST   /api/v1/provider/services
GET    /api/v1/provider/services/my
PUT    /api/v1/provider/services/{serviceId}
DELETE /api/v1/provider/services/{serviceId}
```

## Services

```text
GET /api/v1/services
GET /api/v1/services/{serviceId}
```

## Test

```text
GET /api/v1/test
```

---

# 38. Current Backend Architecture

The backend follows a layered architecture:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
PostgreSQL
```

Detailed flow:

```text
HTTP Request
     |
     v
Controller
     |
     v
DTO Validation
     |
     v
Business Service
     |
     v
JPA Repository
     |
     v
PostgreSQL
     |
     v
Entity
     |
     v
Response DTO
     |
     v
HTTP Response
```

---

# 39. Security Architecture

```text
Client
  |
  | Authorization: Bearer JWT
  v
JwtAuthenticationFilter
  |
  | Validate JWT
  v
SecurityContext
  |
  v
Spring Security
  |
  | Role Check
  v
Controller
  |
  v
Business Logic
```

---

# 40. Current Business Flow

At the current stage:

```text
1. Provider registers
        ↓
2. Provider logs in
        ↓
3. Backend generates JWT
        ↓
4. Provider sends JWT
        ↓
5. Provider creates service
        ↓
6. Service stored in PostgreSQL
        ↓
7. Customer logs in
        ↓
8. Customer requests services
        ↓
9. Customer views service details
```

---

# 41. Testing

APIs are currently being tested using:

```text
Postman
```

Authentication is tested with JWT Bearer tokens.

Example:

```http
Authorization: Bearer <TOKEN>
```

The backend APIs have been successfully tested for:

- Registration
- Login
- JWT authentication
- User profile
- Role authorization
- Provider service creation
- Provider service listing
- Service browsing
- Service details
- Service update
- Service deletion
- Provider ownership validation

---

# 42. Git Workflow

The project is maintained using Git and GitHub.

Recommended workflow:

```text
main
  |
  ├── feature/authentication
  |
  ├── feature/service-management
  |
  ├── feature/booking-system
  |
  └── feature/frontend
```

Feature work should be developed in feature branches and merged into `main` after testing.

---

# 43. Commit Convention

The project uses descriptive commit messages.

Examples:

```bash
git commit -m "feat: implement authentication"
```

```bash
git commit -m "feat: add service management with provider ownership"
```

```bash
git commit -m "fix: resolve JWT authentication issue"
```

Recommended prefixes:

```text
feat     New feature
fix      Bug fix
refactor Code restructuring
docs     Documentation
test     Tests
chore    Configuration/maintenance
```

---

# 44. Current Project Status

```text
Project Setup                  ✅
PostgreSQL Setup               ✅
User Registration              ✅
User Login                     ✅
BCrypt Password Hashing        ✅
JWT Generation                 ✅
JWT Validation                 ✅
JWT Filter                     ✅
Protected APIs                 ✅
Role-Based Authorization       ✅

Service Entity                 ✅
Service Repository             ✅
Create Service                 ✅
View Own Services              ✅
Browse Services                ✅
View Service Details           ✅
Update Service                 ✅
Delete Service                 ✅
Provider Ownership Security    ✅

React Frontend                 ⏳
Booking System                 ⏳
Booking Status Management      ⏳
Search & Filters               ⏳
Reviews & Ratings              ⏳
Admin Dashboard                ⏳
Deployment                     ⏳
```

---

# 45. Next Development Phase

## Phase 8 — Booking System

The next major feature is the booking system.

Expected flow:

```text
Customer
   |
   v
Browse Services
   |
   v
Select Service
   |
   v
Choose Date/Time
   |
   v
Create Booking
   |
   v
Provider
   |
   v
Accept / Reject
   |
   v
Booking Status
```

Expected booking statuses:

```text
PENDING
ACCEPTED
REJECTED
CANCELLED
COMPLETED
```

---

# 46. Future Booking Database Design

Planned `bookings` table:

```text
id
customer_id
service_id
provider_id
booking_date
booking_time
status
address
notes
created_at
updated_at
```

Relationship:

```text
Customer
    |
    | 1
    |
    | *
    v
 Booking
    ^
    |
    | *
    |
    | 1
 Provider

Booking
    |
    | *
    |
    | 1
    v
 Service
```

---

# 47. Future Features

After the booking system, the project may include:

### Customer

- Booking history
- Cancel booking
- Booking status tracking
- Reviews
- Ratings

### Provider

- Booking dashboard
- Accept/reject booking
- Mark booking completed
- Service management dashboard

### Admin

- User management
- Provider management
- Service management
- Booking monitoring
- Basic analytics

### Platform

- Search
- Category filters
- Location-based filtering
- Notifications
- Email notifications
- Payment integration
- Deployment

---

# 48. Monetization Possibilities

The project is being designed as a potential real-world platform.

Possible revenue models:

## Commission

Platform takes a percentage from each completed booking.

Example:

```text
Service price = ₹500

Platform commission = 10%

Platform earns = ₹50
Provider receives = ₹450
```

## Provider Subscription

Providers can eventually pay a monthly subscription for premium features.

Example:

```text
Free
₹0/month

Premium
₹299/month
```

Possible premium features:

- Better visibility
- More service listings
- Booking analytics
- Priority placement

## Featured Services

Providers can pay to promote their services.

Example:

```text
Featured AC Repair
Featured Plumbing
Featured Electrician
```

These monetization features are future possibilities and are not part of the current MVP.

---

# 49. MVP Scope

To keep the project small and practical, the initial MVP will focus on:

```text
Authentication
     +
Provider Service Management
     +
Customer Service Browsing
     +
Booking System
     +
Basic Booking Status
     +
React Frontend
```

Advanced features such as:

```text
Payments
Real-time notifications
Advanced analytics
Maps
AI recommendations
```

will only be considered after the MVP is complete.

---

# 50. Development Philosophy

This project is being developed incrementally.

Each phase follows:

```text
Design
  ↓
Entity
  ↓
Repository
  ↓
DTO
  ↓
Service
  ↓
Controller
  ↓
Security
  ↓
Postman Testing
  ↓
Git Commit
```

The goal is to understand each part instead of generating the entire application at once.

---

# 51. Local Development

## Start PostgreSQL

Ensure PostgreSQL is running locally.

## Start Backend

Run:

```text
LocalServiceBookingApplication
```

Backend:

```text
http://localhost:8080
```

## Test Backend

```text
GET /api/v1/test
```

---

# 52. Important Development Notes

### Never commit secrets

Do not commit:

```text
Database passwords
JWT secrets
API keys
Email passwords
Production credentials
```

Use environment variables or local configuration for secrets.

### Do not expose passwords

User password hashes should never be returned in API responses.

Use DTOs for API responses.

### Validate ownership

Whenever a provider updates/deletes a resource, verify that the authenticated provider owns that resource.

---

# 53. Final Vision

The final application will work approximately like this:

```text
                    LOCALFIX
                       |
        +--------------+--------------+
        |                             |
     CUSTOMER                      PROVIDER
        |                             |
        v                             v
   Browse Services              Manage Services
        |                             |
        v                             v
   Select Service               Receive Booking
        |                             |
        v                             v
     Booking  ----------------> Provider
        |
        v
   Booking Status
        |
        v
    Completed
        |
        v
   Rating / Review
```

The objective is to create a **small, realistic, portfolio-ready service marketplace** rather than an unnecessarily large application.

---

## Current Milestone

**Completed through Step 85**

Current milestone:

```text
Authentication + Authorization
            +
Service Management
            +
Provider Ownership Security
```

Next milestone:

```text
Booking System
```
