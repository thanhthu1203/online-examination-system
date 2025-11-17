```markdown
# Online Examination System

Starter project: Java Spring Boot backend + vanilla JavaScript frontend

Contents
- Backend (Spring Boot, Java 17)
  - JPA entity models mapped to the online_exam_db schema
  - JWT authentication (login issues JWT with role claim)
  - Spring Security filter and config enforcing role-based access
  - AuthService (login/register), Course/Exam/Result controllers
  - CSV bulk result upload endpoint
  - Flyway migration + seed data
  - OpenAPI (Swagger) UI via springdoc
  - Unit/integration test examples
- Frontend (static)
  - Responsive SPA (index.html + CSS + JS)
  - Student and Admin dashboards, calendar view, CSV upload UI
- Dev infra
  - Dockerfiles for backend & frontend
  - docker-compose to run MySQL, backend and frontend
  - GitHub Actions CI workflow

Quick start (local)
1. Ensure you have Docker and docker-compose installed.
2. Clone this repo (or put these files in a folder).
3. Build & run:
   docker-compose up --build
4. Open frontend: http://localhost:3000
   Swagger UI: http://localhost:8080/swagger-ui.html

Notes
- Replace jwt.secret in backend/src/main/resources/application.yml with a secure random secret (>= 32 chars) before production.
- The example uses default MySQL root password `example` for local development; change in production.
- CI workflow uses MySQL service for tests.

Developer tips
- To build backend locally:
  cd backend
  mvn -B -DskipTests clean package
- To run backend jar:
  java -jar target/online-exam-backend-0.0.1-SNAPSHOT.jar

If you want, I can:
- Create a ZIP archive of the scaffold,
- Produce a ready-to-run GitHub repository (I can't push directly; you'll push from your machine),
- Or update any file to match your preferred naming or DTO shapes.
```