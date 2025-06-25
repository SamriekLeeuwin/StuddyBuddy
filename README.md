
# StuddyBuddy – AI-powered learning assistant for students

StuddyBuddy is a full-stack web application designed to help students manage their study materials and create personalized learning plans. Users can upload assignments, notes, or documents, and the application uses AI to analyze the content and generate a task-based study schedule.

The backend is developed in Java using Spring Boot, with secure JWT authentication (RSA public/private key encryption). The frontend is built in React.

## What does StuddyBuddy do?

- Users can register and log in securely using JWT
- Students can upload study material (such as PDFs, text, scans)
- AI analyzes the uploaded material and identifies key topics
- Based on this, the system generates a structured learning plan with tasks
- Students follow the tasks to study more effectively

## Technologies used

**Backend**
- Java 17
- Spring Boot 3.x
- Spring Security 6.1
- JWT (RSA encryption)
- Lombok
- Maven
- (Optional) PostgreSQL or H2 database

**Frontend**
- React (TypeScript)
- Axios for HTTP communication
- Vite or CRA for frontend tooling

**AI / OCR**
- Optional: integration with OCR tools 

## Project structure (backend)

```
src/
├── controller/      # API endpoints (authentication, task management)
├── service/         # Business logic (JWT generation, AI processing)
├── model/           # Domain models (User, Role, Task)
├── repository/      # Data access using Spring Data JPA
├── config/          # JWT key loading, security setup
└── resources/
    ├── application.yaml
    └── jwt/
        ├── app.key
        └── app.pub
```

## Authentication flow

1. User signs up or logs in using their credentials
2. Upon login, a JWT is generated and returned
3. The frontend stores the token and uses it in `Authorization: Bearer <token>` headers
4. Secured backend endpoints verify the token and role claims
5. Users can only access features appropriate to their role (e.g. USER, ADMIN)

## Getting started

1. Clone the repository:

```bash
git clone https://github.com/yourusername/studdybuddy.git
cd studdybuddy
```

2. Make sure the following RSA files are in `src/main/resources/jwt/`:
   
   - `app.pub` (public key)

3. Add an `application.yaml` file like this:

```yaml
jwt:
  issuer: studdybuddy-app
  ttl: 1h
```

4. Run the backend:

```bash
./mvnw spring-boot:run
```

5. (Optional) Start the frontend separately in the `frontend/` folder if applicable

## Example JWT payload

```json
{
  "sub": "student1",
  "roles": ["ROLE_USER"],
  "iat": 1719239104,
  "exp": 1719242704,
  "iss": "studdybuddy-app"
}
```

## What I learned

This project helped me improve my understanding of:


## Contact

If you'd like to know more about the project, collaborate, or give feedback, feel free to contact me:

