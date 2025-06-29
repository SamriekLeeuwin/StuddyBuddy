
# StuddyBuddy

I'm developing StuddyBuddy, a full-stack AI-powered study assistant. The goal is to help students upload their notes or documents and turn them into structured, personalized study plans using artificial intelligence.

This project is part of my third-year computer science journey.



#### Sign up a new user

```http
POST /api/auth/signup
```

**Request Body**

| Field    | Type   | Description |
| -------- | ------ | ----------- |
| username | string | Required    |
| password | string | Required    |

**Response**
Returns a JWT token and the created username.

---

#### Log in

```http
POST /api/auth/login
```

**Request Body**

| Field    | Type   | Description |
| -------- | ------ | ----------- |
| username | string | Required    |
| password | string | Required    |

**Response**
Returns a JWT token if authentication is successful.

---

### Study Materials

#### Get all study materials

```http
GET /api/materials
```

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Authorization | Bearer <JWT token> |

**Response**
List of study materials for the authenticated user.

---

#### Create a study material

```http
POST /api/materials
```

**Headers**

| Key           | Value              |
| ------------- | ------------------ |
| Authorization | Bearer <JWT token> |

**Request Body**

| Field       | Type   | Description |
| ----------- | ------ | ----------- |
| title       | string | Required    |
| description | string | Required    |

**Response**
Returns the created material object.

---

### File Upload

#### Upload a file to a material

```http
POST /api/materials/{materialId}/upload
```

**Headers**

| Key           | Value               |
| ------------- | ------------------- |
| Authorization | Bearer <JWT token>  |
| Content-Type  | multipart/form-data |

**Form Data**

| Field | Type | Description              |
| ----- | ---- | ------------------------ |
| file  | File | Required. File to upload |

**Path Parameter**

| Name       | Type   | Description        |
| ---------- | ------ | ------------------ |
| materialId | string | ID of the material |

**Response**
Returns metadata of the uploaded file.

---

## Features

*  **JWT Authentication** using RSA key pairs for secure access
*  **User Registration & Login** via Spring Security
*  **Upload and Manage Study Materials** with file validation
*  **RESTful API** with endpoints for auth, users, and materials
* **Supabase PostgreSQL** database with SSL encryption
*  **JWT Token Validation** via Bearer tokens and RSA public key
*  **Public/Private Key Support** loaded from classpath for JWT
*  **AI Integration (Planned)**: automatic content analysis and study task generation
*  **Role-Based Access Control** (USER / ADMIN)
*  **DTO Validation and Exception Handling** with clear HTTP responses
*  **Scalable Backend Architecture** using Spring Boot and JPA


## Tech Stack

###  Implemented

- **Backend:** Java, Spring Boot, Spring Security, JPA (Hibernate)
- **Database:** PostgreSQL (Supabase)
- **Authentication:** JWT (RSA encryption)
- **AI/OCR:** Apache PDFBox, Tesseract OCR
- **Build Tool:** Maven

###  Planned

- **Frontend:** React, Vite, Tailwind CSS
- **Deployment:** Docker, Supabase Edge Functions (optional)
 
---

## Lessons Learned

While building **StuddyBuddy**, I ran into several real world backend challenges that taught me a lot about secure authentication, Spring Boot architecture, and debugging distributed systems. Here are the most important lessons:

* **Spring Boot & JPA Transactions:**
  I encountered errors like `Could not commit JPA transaction`, often caused by misconfigured entities or missing foreign key constraints. I resolved these by refining the entity relationships, validating service-layer boundaries, and ensuring data integrity in DTOs.

* **Project Structure & Maven Build Failures:**
  Maven failed to recognize the project at first due to misplaced `pom.xml` files and incorrect directory hierarchy. I learned the importance of Maven's standard structure (`src/main/java`, etc.) and fixed path issues to enable successful builds.

* **JWT Configuration with RSA Keys:**
  The app failed to start due to missing RSA keys for JWT token signing. I learned to:

    * Generate RSA key pairs (`private.pem`, `public.pem`)
    * Store them securely under `src/main/resources/jwt`
    * Reference them properly in `application.yml` using `classpath:`

* **Supabase PostgreSQL Integration:**
  Connecting to Supabase required understanding the difference between direct and pooler URIs, dealing with SSL errors, and encoding special characters in the password. After several failed attempts, I successfully configured a secure, long lived connection.

* **Authentication Chain & Role Mapping:**
  One major bug was in passing user roles correctly across the JWT → security context → controller → service. Certain endpoints rejected valid users due to:

    * JWT not including `roles` in its claims
    * Incorrect authority mapping in the security config
    * Method security misalignment (`@PreAuthorize`)

  I fixed this by embedding roles in the JWT, ensuring Spring Security reads them properly, and aligning them with role based logic in the controller/service layers.

* **DTO Mismatches & Repository Type Errors:**
  I faced several `ClassCastException` and `Invalid type` errors due to inconsistencies between DTOs, Entities, and JPA Repository methods. I solved this by:

    * Creating specific DTO classes for input/output
    * Using mappers or constructors to convert between them
    * Clearly separating domain logic from controller input

* **Bearer Token Usage & Postman Testing:**
  During testing, I learned how to set Authorization headers properly, troubleshoot `401 Unauthorized` errors, and verify JWT expiration. This improved my API debugging and security validation skills.



---
