```markdown
# Online Examination System — Quick Start (Frontend / Backend / Database)

This README focuses on the three core parts and includes clickable links you can use immediately.

Repository (replace or visit)
- Project repo (create or use): https://github.com/thanhthu1203/online-examination-system

1. Clone Project
```
git clone https://github.com/thanhthu1203/online-examination-system.git
cd online-examination-system
```

2. Backend Setup (Java Spring Boot)
----------------------------------

**Prerequisites**
- Java 17+
- Maven
- MySQL 8 (or compatible)

**Install dependencies & build**
```
cd backend
mvn clean install
```

**Configure Database**
- Edit: https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/application.yml
- Example values (local dev):
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/online_exam_db?useSSL=false&serverTimezone=UTC
    username: root
    password: example

jwt:
  secret: change_this_to_a_long_secret_key_at_least_32_chars_long_for_HS256
  expiration-ms: 3600000
```

**Create DB & seed data**
- If you use Docker Compose (recommended), DB + migrations + seed run automatically:
  ```
  docker-compose up --build
  ```
- If running MySQL locally, start MySQL and then start backend; Flyway will create the schema and load `data.sql`.

**Run backend**
```
cd backend
mvn spring-boot:run
```
- Swagger (API docs) will be available when backend is running: http://localhost:8080/swagger-ui.html

3. Frontend Setup (Static HTML + JS)
------------------------------------

**Prerequisites**
- Node.js (optional, only if you use a dev server)
- A static file server (or just use Docker Compose)

**Serve frontend (quick)**
- Option A — Docker Compose (recommended): serves frontend at http://localhost:3000
- Option B — Simple local server:
  ```
  cd frontend
  python3 -m http.server 8080
  # open http://localhost:8080
  ```

**Files to check**
- Frontend entry: https://github.com/thanhthu1203/online-examination-system/blob/main/frontend/index.html
- API client (update backend URL if needed): https://github.com/thanhthu1203/online-examination-system/blob/main/frontend/js/api.js

4. Database (schema & seed)
---------------------------
- Flyway migration script: https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/db/migration/V1__create_tables.sql
- Seed data: https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/data.sql

5. Important commands (summary)
```
# Clone
git clone https://github.com/thanhthu1203/online-examination-system.git
cd online-examination-system

# Full stack (Docker)
docker-compose up --build

# Backend only
cd backend
mvn clean install
mvn spring-boot:run

# Frontend simple server
cd frontend
python3 -m http.server 8080
```

6. Useful quick links (clickable)
- Repository: https://github.com/thanhthu1203/online-examination-system
- Backend config file: https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/application.yml
- Flyway migration (schema): https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/db/migration/V1__create_tables.sql
- Seed data: https://github.com/thanhthu1203/online-examination-system/blob/main/backend/src/main/resources/data.sql
- Frontend index: https://github.com/thanhthu1203/online-examination-system/blob/main/frontend/index.html
- Swagger (when running locally): http://localhost:8080/swagger-ui.html
- Frontend (when running via docker-compose): http://localhost:3000

7. Notes & tips
- Replace the repo URL above with your actual repo if different.
- Change `jwt.secret` to a secure random value before deploying to production.
- If frontend gets CORS errors during local dev, ensure backend allows requests from your frontend origin (or use Docker Compose so both run locally).

```
