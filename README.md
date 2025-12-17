# TechHealth-Backend

Spring Boot REST API for a small healthcare monitoring system using a hybrid database setup:
- **MySQL (SQL):** core entities + relationships
- **MongoDB (NoSQL):** vital readings + medication rules

## Tech Stack
Java 17, Spring Boot, Spring Data JPA, Spring Data MongoDB, MySQL, MongoDB, Maven, Postman

## What’s Implemented
- CRUD for all entities (Controllers + Services + Repositories)
- Relationships:
    - One-to-Many / Many-to-One
    - Many-to-Many using join entities (**UserRole**, **PatientFacility**)
- MongoDB collections:
    - **VitalReading**
    - **MedicationRule**
- One class per Java file (prof note)

## Run the Project
### Requirements
- Java 17+
- MySQL running
- MongoDB running

### Setup
1) Create MySQL database (example: `tech_health`)
2) Run scripts:
- `db/mysql/schema.sql`
- `db/mysql/seed.sql`

3) Create config:
- Copy `src/main/resources/application-example.properties`
- Rename to `src/main/resources/application.properties`
- Update MySQL username/password + DB name if needed

### Start Backend
```cmd
.\mvnw.cmd spring-boot:run

## API + Postman
Base URL: `http://localhost:8080`

Postman files:
- `postman/TechHealth API Collection.postman_collection.json`
- `postman/TechHealth.postman_environment.json`

Environment variable:
- `baseUrl = http://localhost:8080`

## Endpoints (summary)
MySQL APIs under `/api/*` (facilities, patients, users, roles, visits, medications, prescriptions, administrations, join tables).
MongoDB: `/api/vital-readings`, `/api/medication-rules`.
