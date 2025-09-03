# Blackjack Reactive API (Spring Boot + WebFlux)

**Base package:** `cat.itacademy.blackjackapi`\
**Architecture:**
`cat.itacademy.blackjackapi.{config,domain,application,infrastructure,web}`\
**Stack:** Java 21, Spring Boot 3.x, WebFlux, Reactive MongoDB, R2DBC
MySQL, MapStruct, Lombok, Swagger/OpenAPI, Flyway.

------------------------------------------------------------------------

## ✨ Goal

100% reactive backend for a **Blackjack game** (1 player vs dealer, no
bets).\
Persistence: - **MySQL (R2DBC):** players and results/history. -
**MongoDB (Reactive):** live game state (game document).

------------------------------------------------------------------------

## 🧱 Package structure

    cat.itacademy.blackjackapi
     ├─ config/                 # Configuration (OpenAPI, Mongo auditing, R2DBC, etc.)
     ├─ domain/
     │   ├─ mysql/
     │   │   ├─ entity/         # PlayerEntity, GameResultEntity
     │   │   └─ repository/     # ReactiveCrudRepository
     │   └─ mongo/
     │       ├─ document/       # GameDocument, HandDocument, ...
     │       └─ repository/     # ReactiveMongoRepository
     ├─ application/
     │   ├─ service/            # Reactive services (interfaces + impl)
     │   └─ mapper/             # MapStruct mappers (Entities/Documents -> DTOs)
     ├─ infrastructure/         # Integrations, adapters (if needed)
     └─ web/
         ├─ controller/         # WebFlux controllers (handlers/routers or @RestController)
         └─ dto/                # Request/response DTOs

------------------------------------------------------------------------

## 🔧 Requirements

-   **Java 21**
-   **Maven 3.9+**
-   **MySQL 8.x** (user with DDL permissions)
-   **MongoDB 6+**

------------------------------------------------------------------------

## ⚙️ Profiles and configuration

Supported profiles:

-   **local**: local development (MySQL and Mongo running locally).
-   **docker**: same as local, but hostnames `mysql` and `mongo`.
-   **prod**: credentials read from environment variables.

Edit `src/main/resources/application.yml` with your credentials/URIs.

Example (local):

``` yaml
spring:
  r2dbc:
    url: r2dbc:pool:mysql://localhost:3306/blackjack
    username: root
    password: root
  flyway:
    url: jdbc:mysql://localhost:3306/blackjack
    user: root
    password: root

  data:
    mongodb:
      uri: mongodb://localhost:27017/blackjack

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

------------------------------------------------------------------------

## ▶️ How to run

### 1) Clone the repository

``` bash
git clone https://github.com/anaberod/S05.01_BlackjackApi.git
cd S05.01_BlackjackApi
```

### 2) Build

``` bash
./mvnw clean package -DskipTests
```

### 3) Run in **local**

``` bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### 4) Run with **Docker Compose** (if you have a compose with MySQL+Mongo)

``` bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

------------------------------------------------------------------------

## 🗄️ Migrations (Flyway)

Scripts in `src/main/resources/db/migration/`:

-   `V1__init.sql` -- tables `players` and `game_results`.
-   `V2__ranking_view.sql` -- ranking view.

They are executed automatically at startup if Flyway is configured.

------------------------------------------------------------------------

## 📚 Swagger / OpenAPI

When the app is running: - **UI:**
`http://localhost:8080/swagger-ui.html` - **JSON:**
`http://localhost:8080/v3/api-docs`

------------------------------------------------------------------------

## 🧪 Tests

``` bash
./mvnw test
```

Includes `spring-boot-starter-test`, `reactor-test`, `mockito-core`,
`mockito-junit-jupiter`.

------------------------------------------------------------------------

## 🧩 Useful notes

-   Make sure **Annotation Processing** is enabled in your IDE for
    **Lombok** and **MapStruct**.
-   Confirm that all classes are under `cat.itacademy.blackjackapi` and
    no old packages remain.
-   For R2DBC MySQL use the driver: `io.asyncer:r2dbc-mysql`. For Flyway
    use `mysql-connector-j` (JDBC) in `runtime`.

------------------------------------------------------------------------

## 📦 Example endpoints

-   `POST /api/games` -- create a new game (player vs dealer)
-   `GET  /api/games/{id}` -- get game state
-   `POST /api/games/{id}/play` -- actions: HIT, STAND, DOUBLE (no bets)
-   `GET  /api/players/{id}` -- get player
-   `PATCH /api/players/{id}` -- rename player
-   `GET  /api/ranking` -- ranking from MySQL (view)

------------------------------------------------------------------------

## 📝 License

MIT (or your choice)
