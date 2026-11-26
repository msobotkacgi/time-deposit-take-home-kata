# Time Deposit API

Spring Boot application for XA Bank time deposits. PostgreSQL stores the plans, and Flyway loads the tables and sample rows on startup.

## Run

Requires Java 24, Maven, and Docker.

```bash
cd java
docker compose up -d
mvn spring-boot:run
```

The API listens on http://localhost:8080. PostgreSQL is published on `localhost:5433` (database, user, and password `timedeposit`) so it does not collide with a PostgreSQL already using port 5432.

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/time-deposits` | Returns every time deposit and its withdrawals. |
| POST | `/time-deposits/balance-updates` | Applies one month of interest and stores the new balances. No request body. |

## Tests

```bash
cd java
mvn test
```
