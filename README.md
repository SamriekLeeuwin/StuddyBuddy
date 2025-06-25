# StuddyBuddy – AI-powered study assistant (Work in Progress)

**StuddyBuddy** is a full-stack project I'm building as a third-year computer science student. It's designed to help students manage their study materials and automatically generate personalized study plans using AI.

I'm currently focusing on the backend, which is being built in Java using Spring Boot. It includes secure JWT authentication using RSA encryption. The frontend will be built in React.

---

##  Project Status

>  This project is a **work in progress**. The backend is under active development. Features like JWT-based authentication and key management are functional. AI analysis and frontend integration are planned for upcoming stages.

---

##  Goals

- Help students turn their notes and documents into structured study tasks
- Secure authentication using JWT and RSA
- Scalable backend structure using Spring Boot and JPA
- (Later) AI analysis for topic extraction and task generation

---

##  JWT Authentication

Authentication is handled using:

- RSA-based public/private key pair
- Spring Security for access control
- Secure JWT generation with role-based claims

Example JWT payload:

```json
{
  "sub": "student1",
  "roles": ["ROLE_USER"],
  "iat": 1719239104,
  "exp": 1719242704,
  "iss": "studdybuddy-app"
}
