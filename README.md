# Exam Seating Arrangement System

A complete web application for managing exam seating arrangements, built with Spring Boot, Thymeleaf, and MySQL.

## Features

- **Public Student Search** — Students can look up their seat assignment by roll number
- **Admin Dashboard** — Overview of students, exams, rooms, and seating plans
- **Students CRUD** — Add, edit, delete, and view students
- **Exams CRUD** — Add, edit, delete, and view exams
- **Rooms CRUD** — Add, edit, delete, and view rooms
- **Seating Plan Generator** — Auto-generate seating arrangements, preview, save as draft, and publish
- **Spring Security** — Secured admin area with form-based login

## Tech Stack

- Java 17+
- Spring Boot 3.2.4
- Thymeleaf (server-side templates)
- Spring Security (form login, BCrypt)
- Spring Data JPA + Hibernate
- MySQL 8 (production) / H2 (tests)
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+

## Setup (Windows PowerShell)

### 1. Set Up the Database

Run this command in PowerShell (using your installed MySQL 8.4 path):

```powershell
Get-Content .\setup_database.sql | & "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe" -u root -p
```

You will be prompted for your MySQL **root** password.

### 2. Set Environment Variable & Run

```powershell
$env:DB_PASSWORD = "<your-mysql-password>"
mvn spring-boot:run
```

Alternatively, you can just double-click **`run.bat`** (for MySQL) or **`run-dev.bat`** (to test instantly using in-memory H2 without MySQL)!


### 4. Open the Application

Navigate to [http://localhost:8080](http://localhost:8080) in your browser.

### Default Admin Login

- **Username:** `admin`
- **Password:** `admin123`

> ⚠ Change the default admin password after first login in a production environment.

## Running Tests

Tests use an embedded H2 database — no MySQL required:

```powershell
mvn test
```

## Project Structure

```
exam-seating-system/
├── pom.xml
├── setup_database.sql
├── README.md
└── src/
    ├── main/
    │   ├── java/com/examseating/
    │   │   ├── ExamSeatingApplication.java
    │   │   ├── config/
    │   │   ├── entity/
    │   │   ├── repository/
    │   │   ├── service/
    │   │   └── controller/
    │   └── resources/
    │       ├── application.properties
    │       └── templates/
    └── test/
        └── java/com/examseating/
```

## API Endpoints

| Method | Path | Access | Description |
|--------|------|--------|-------------|
| GET | `/` | Public | Home page |
| GET | `/search` | Public | Student seat search |
| GET | `/login` | Public | Login page |
| GET | `/admin/dashboard` | Admin | Dashboard |
| GET/POST | `/admin/students/**` | Admin | Students CRUD |
| GET/POST | `/admin/exams/**` | Admin | Exams CRUD |
| GET/POST | `/admin/rooms/**` | Admin | Rooms CRUD |
| GET/POST | `/admin/seating/**` | Admin | Seating management |

## License

This project is for educational purposes.
