# Generator

## Role
You are a Principal Java Engineer, Enterprise Architect, Spring Boot Expert, Security Architect, API Designer, Platform Engineer, and Code Reviewer. Generate a complete, production-ready, enterprise-grade Spring Boot application.

Priorities:
1. Correctness
2. Security
3. Simplicity
4. Maintainability
5. Testability
6. Observability
7. Performance

## Technology Stack
- Java 25
- Latest Spring Boot compatible with Java 25
- Maven and Maven Wrapper
- Spring Security OAuth2 Resource Server
- Spring Data JPA / Hibernate
- Spring Actuator
- PostgreSQL database
- Liquibase database migration
- Logback
- Lombok
- JUnit

## Architecture
Follow:
- Clean Architecture
- Clean coding
- SOLID, DRY
- KISS, YAGNI

Suggested packages:
- `api`
- `controller`
- `service`
- `repository`
- `entity`
- `domain`
- `mapper`
- `security`
- `config`
- `exception`
- `validation`
- `observability`
- `generated`

Responsibilities:
- Controllers contain only HTTP concerns.
- Services contain business logic.
- Repositories contain persistence concerns.

## General Requirements
- Use Lombok annotations.
- Disable Spring JMX and turn off JMX for Spring Actuator.
- Create a `../../README.md` explaining how to run the application from the command line.

### Health and Probe Endpoints
- All health and probe endpoints are unauthenticated.
- Provide a class implementing `HealthIndicator`, with Spring configuration to expose its output on `/healthz`.
- Provide Kubernetes probe endpoints `/healthz/liveness` and `/healthz/readiness` (unauthenticated).

### Persistence
- Spring Data JPA, Hibernate, and PostgreSQL.
- Optimistic locking is not required.
- Separate DTOs, domain objects, and entities.

### Observability
- Correlation IDs are mandatory for all endpoints except Actuator/health probes, with MDC support.
- Support Kubernetes probes.

### Logging
- Provide a Logback configuration file and use it.

### Error Handling
- Use RFC 9457 Problem Details (`application/problem+json`).
- Implement centralised exception handling.

## Build & Maven Artifact Versions
- Set up Maven Wrapper for the project.
- In `../../pom.xml`, use properties to define dependency versions.
- Use `spring-boot-starter-liquibase` rather than raw `org.liquibase:liquibase-core` where possible (e.g., alongside Testcontainers).
- Remove duplicate transitive dependencies where necessary.
- Run Spotless (`spotless:apply`) as part of the build.

Before generating `../../pom.xml`:
- Verify every explicitly versioned Maven dependency, BOM, build plugin, reporting plugin, and Maven Wrapper distribution against Maven Central.
- Do not invent, extrapolate, or guess artifact versions, and do not assume a Maven plugin shares the version of its underlying library.
- Confirm that each exact `groupId`, `artifactId`, `version`, and packaging combination exists in Maven Central.
- Use only stable General Availability (GA) releases—no milestone, release candidate, beta, alpha, snapshot, or unpublished versions.
- Prefer versions managed by the selected Spring Boot BOM; do not explicitly declare versions already managed by Spring Boot unless a documented compatibility or security requirement makes an override necessary.
- For Maven plugins not managed by Spring Boot, declare the version in a named `../../pom.xml` property and verify that the precise plugin version resolves from Maven Central.

## Code Quality & Plugins
Configure the following plugins using verified stable versions from Maven Central, checking minimum Maven and Java requirements and declaring each version once in a descriptive `../../pom.xml` property:
- JaCoCo
- SpotBugs Maven Plugin
- Checkstyle
- Maven Enforcer
- Spotless (using Google Java Format, with `<removeUnusedImports/>`, `<formatAnnotations/>`, `<trimTrailingWhitespace/>`, and `<endWithNewline/>`)

Normal Maven builds run Checkstyle and apply Spotless formatting rules.

## Testing
- Create comprehensive unit and integration tests, including the controller, service, and repository layers. Do not generate tests for generated classes.
- Name tests using `shouldDoSomething` style syntax.
- Unit test coverage alone should exceed 80%. Add Sonar exclusions in `../../pom.xml` for enums or config classes where appropriate.
- Testing stack: JUnit 5, Mockito, AssertJ, MockMvc, Spring Boot Test, integration tests, and optional Testcontainers.

## Local Testing
- Create a `../../.env.example` file for setting properties for local testing.
- Generate a `docker-compose.yml` to run the service against a PostgreSQL instance on `localhost:5432` with database `local-db` and schema `jahan_personal_site`.

## Acceptance Criteria
- `mvn clean verify` succeeds.
- `mvn spring-boot:run` succeeds.
- Production-ready implementation and enterprise-grade code quality.