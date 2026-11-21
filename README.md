Front End: https://github.com/Jahanzaib-Alam/jahan-personal-site
# Jahan Personal Site Backend

Spring Boot service providing prayer-times and Steam activity APIs.

## Requirements

- Java 25
- Maven 3.9+ or the included Maven Wrapper
- Docker and Docker Compose

## Run locally

1. Start PostgreSQL:

   ```bash
   docker compose up -d
   ```

2. Copy `.env.example` to `.env` and provide a Steam API key and user ID.
3. Start the application:

   ```bash
   ./mvnw spring-boot:run
   ```

The service listens on `http://localhost:8081` by default.

## Health endpoints

- `GET /healthz`
- `GET /healthz/liveness`
- `GET /healthz/readiness`
- `GET /actuator/health`

These endpoints do not require authentication.

## Build and test

```bash
./mvnw clean verify
```
