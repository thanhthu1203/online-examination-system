```markdown
Online Examination System — Quick Setup Guide
============================================

A minimal, ready-to-run scaffold:
- Backend: Java 17 + Spring Boot (JPA, Flyway, Spring Security with JWT)
- Database: MySQL (Docker recommended)
- Frontend: Static HTML + vanilla JavaScript + CSS (served via nginx or any static server)
- Dev infra: Docker Compose, Flyway DB migrations, example CI workflow

1. Clone Project
---------------
git clone [REPO_URL]
cd online-examination-system

2. Backend Setup (Java / Spring Boot)
------------------------------------

Prerequisites
- Java 17+
- Maven (if you want to build locally)
- MySQL (or Docker)
- (Optional) Docker & docker-compose for full-stack local run

Install dependencies & build
- From repository root (or backend/):
  cd backend
  mvn clean install

Configuration (database + JWT)
- Default config: backend/src/main/resources/application.yml
- Update DB connection and JWT secret as needed. Example:

spring:
  datasource:
    url: jdbc:mysql://db:3306/online_exam_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: root
    password: example

jwt:
  secret: change_this_to_a_long_secret_key_at_least_32_chars_long_for_HS256
  expiration-ms: 3600000

Create database & run migrations
- If using Docker Compose (recommended) the DB and Flyway run automatically (Flyway runs at backend startup).
- If running MySQL locally, create DB manually or let Flyway create it:
  - Start MySQL
  - Ensure application.yml points to your DB
  - On backend start, Flyway will apply `backend/src/main/resources/db/migration/V1__create_tables.sql` and seed `data.sql`

Run backend
- Option A: run with Maven (local dev)
  cd backend
  mvn spring-boot:run
- Option B: run packaged jar
  cd backend
  mvn -DskipTests clean package
  java -jar target/online-exam-backend-0.0.1-SNAPSHOT.jar

3. Frontend Setup (Static HTML + JS)
------------------------------------

Prerequisites
- (Optional for development) Node.js if you want to serve using a simple dev server
- Otherwise frontend is static and can be served by nginx or any static host

Serve locally (simple):
- Option A: open frontend/index.html in browser (file://) — some browsers block module imports from file system
- Option B (recommended): run a simple HTTP server from project root:
  # Using Python 3
  cd frontend
  python3 -m http.server 8080
  # Open http://localhost:8080

Serve with Docker (provided)
- The repo includes a frontend Dockerfile and docker-compose service. Docker Compose will build and serve on port 3000.

4. Full stack with Docker Compose (recommended)
-----------------------------------------------

From repository root:
- Build & run everything:
  docker-compose up --build

- Services (default ports):
  - MySQL: 3306
  - Backend: 8080
  - Frontend: 3000 (nginx static)

- Stop:
  docker-compose down
- Remove volumes (reset DB):
  docker-compose down -v

5. Quick command cheatsheet
---------------------------
# Clone
git clone [REPO_URL]
cd online-examination-system

# Build backend
cd backend
mvn clean install

# Run backend (dev)
mvn spring-boot:run

# Run full stack (docker)
cd ..
docker-compose up --build

# Serve frontend locally (if not using docker)
cd frontend
python3 -m http.server 8080

6. Change database configuration
--------------------------------
Edit backend/src/main/resources/application.yml or provide environment variables:
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD

When using docker-compose the backend service reads environment variables defined in docker-compose.yml. For production, inject secrets via environment or vault.

7. JWT & security notes
-----------------------
- Replace `jwt.secret` in application.yml with a secure random secret (>= 32 chars).
- Tokens are returned by POST /auth/login as JSON: { "token": "<JWT>", "role": "STUDENT" | "ADMIN" }
- Include token in requests:
  Authorization: Bearer <JWT>

8. CSV bulk result upload (admin)
---------------------------------
- Endpoint: POST /results/upload-csv (multipart/form-data) with `file` field.
- CSV format (no header): studentId,examId,score
  Example:
  s1001,e1001,92
  s1002,e1001,85

9. API examples (curl)
----------------------
# Register student
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"Student_ID":"s1234","SName":"Test Student","Phone":"012345678","Password":"pass"}'

# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"s1234","password":"pass"}'

# List courses (use token from login)
curl -H "Authorization: Bearer <JWT>" http://localhost:8080/courses

# Enroll current student in course (student must be logged in)
curl -X POST -H "Authorization: Bearer <JWT>" http://localhost:8080/courses/CS101/enroll

# Upload CSV (admin)
curl -X POST -H "Authorization: Bearer <ADMIN_JWT>" -F "file=@/path/to/results.csv" http://localhost:8080/results/upload-csv

10. Tests & CI
--------------
- Backend tests:
  cd backend
  mvn test

- CI:
  - A GitHub Actions workflow is included (.github/workflows/ci.yml) which builds and runs tests on push/PR.

11. Troubleshooting (common issues)
-----------------------------------
- Backend fails to connect to DB:
  - Check DB URL / credentials in application.yml or environment variables.
  - If using docker-compose, ensure `db` service is healthy before backend starts (docker-compose handles basic depends_on + healthcheck).
- Flyway migration errors:
  - Inspect backend logs for root cause. If DB contains conflicting schema, consider dropping DB (dev only) and re-running.
- Frontend cannot call API:
  - Ensure API_BASE at top of frontend/js/api.js points to backend (default http://localhost:8080)
  - Check browser console for CORS errors; allow CORS in dev if needed.
- JWT unauthorized:
  - Ensure Authorization
