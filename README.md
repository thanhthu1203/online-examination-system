```markdown
# Online Examination System

1. Clone Project
```
git clone [REPO_URL]
cd online-examination-system
```

2. Backend Setup (Java Spring Boot)
----------------------------------

Prerequisites
- Java 17 trở lên
- Maven
- MySQL 8 (hoặc tương thích)

Cài đặt dependencies
```
cd backend
mvn clean install
```

Cấu hình Database
- Mở file: `backend/src/main/resources/application.yml` (hoặc `application.properties`)
- Chỉnh sửa thông tin kết nối database cho phù hợp với máy của bạn (username, password, url, v.v.)

Ví dụ (application.yml):
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

Tạo Database & Dữ liệu mẫu
- Nếu dùng Docker Compose: khởi chạy `docker-compose up --build` sẽ tự tạo DB và chạy Flyway migration + seed data.
- Nếu chạy MySQL cục bộ: khởi chạy MySQL, sau đó start backend; Flyway sẽ tạo schema và load `data.sql`.

Chạy backend
```
cd backend
mvn spring-boot:run
```

3. Frontend Setup (Static HTML / JS)
------------------------------------

Prerequisites
- Node.js (khuyến nghị >= 14) — chỉ cần khi bạn muốn dùng dev server
- npm hoặc yarn (nếu dùng dev server)

Cài đặt dependencies (nếu có package.json)
```
cd frontend
npm install
```

Chạy frontend (tùy chọn)
- Option A — Dùng dev server (nếu có):
```
npm run dev
# Truy cập http://localhost:3000
```
- Option B — Dùng server tĩnh (không cần Node):
```
cd frontend
python3 -m http.server 8080
# Truy cập http://localhost:8080
```

4. Tổng hợp các lệnh cần thiết
```
# Clone project
git clone [REPO_URL]
cd online-examination-system

# Backend
cd backend
mvn clean install
mvn spring-boot:run

# Frontend (optional dev server)
cd frontend
npm install
npm run dev

# Or serve static files
cd frontend
python3 -m http.server 8080
```

5. Chỉnh sửa thông tin database
- File cấu hình backend: `backend/src/main/resources/application.yml` (hoặc `application.properties`)
- Đảm bảo chỉnh:
  - host (localhost hoặc tên container)
  - port (mặc định MySQL 3306)
  - username
  - password
  - database name (ví dụ `online_exam_db`)

6. Thư viện JDBC / Dependencies
- JDBC driver (MySQL connector) được cấu hình bằng Maven trong `backend/pom.xml` (không cần đặt thủ công .jar).
- Nếu bạn muốn dùng driver thủ công, đặt .jar trong thư mục lib và cập nhật classpath (không khuyến nghị).

Ghi chú ngắn
- Để chạy đầy đủ (DB + Backend + Frontend) nhanh nhất, dùng Docker Compose:
  ```
  docker-compose up --build
  ```
- Đừng quên đổi `jwt.secret` trong `application.yml` thành chuỗi bí mật đủ dài trước khi deploy.

```
