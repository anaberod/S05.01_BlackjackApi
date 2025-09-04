# Blackjack Reactive API (Spring Boot + WebFlux)

**Base package:** `cat.itacademy.blackjackapi`  
**Architecture:** `cat.itacademy.blackjackapi.{config,domain,application,infrastructure,web}`  
**Stack:** Java 21, Spring Boot 3.x, WebFlux, Reactive MongoDB, R2DBC MySQL, MapStruct, Lombok, Swagger/OpenAPI, Flyway.

---

## ✨ Goal

A **100% reactive** backend for a **Blackjack** game (1 player vs dealer, no bets).  
Persistence split by concern:
- **MongoDB (reactive):** live game state (game documents).
- **MySQL (R2DBC):** players and results/ranking (**ranking is mocked in Level 1**).

---

## 🧱 Package structure

```
cat.itacademy.blackjackapi
 ├─ config/                 # Configuration (OpenAPI, Mongo auditing, R2DBC, etc.)
 ├─ domain/
 │   ├─ mysql/
 │   │   ├─ entity/         # PlayerEntity, ...
 │   │   └─ repository/     # R2DBC repositories (domain contracts)
 │   └─ mongo/
 │       ├─ document/       # GameDocument, HandDocument, CardDocument, MoveDocument
 │       └─ repository/     # ReactiveMongoRepository (GameDocumentRepository)
 ├─ application/
 │   ├─ dto/                # Request/response DTOs (CreateGameRequest, GameResponse, PlayerView, RankingItem...)
 │   ├─ mapper/             # MapStruct mappers (MapStructConfig, PlayerMapper, GameResultMapper, GameDtoMapper)
 │   └─ service/            # Reactive services (interfaces + impl)
 ├─ infrastructure/         # Adapters/implementations for repositories (when needed)
 └─ web/
     └─ controller/         # WebFlux controllers (@RestController)
```

---

## ✅ Current status (Level 1)

- **DTOs** in `application/dto`:
    - Requests: `CreateGameRequest`, `PlayRequest`, `PlayerRenameRequest`
    - Views: `PlayerView`, `GameResponse` (with nested `HandView`, `CardView`, `MoveView`), `RankingItem`
- **Mappers** in `application/mapper`:
    - `MapStructConfig` (`componentModel = "spring"`)
    - `PlayerMapper` (MySQL → `PlayerView`)
    - `GameResultMapper` (MySQL projection → `RankingItem`, plus `fromFields(...)` for mock data)
    - `GameDtoMapper` (Mongo → `GameResponse`), null-safe and list-safe
- **Services** (reactive):
    - `GameServiceImpl` → uses `GameDtoMapper`; stub logic storing to Mongo
    - `PlayerServiceImpl` → uses `PlayerMapper`; rename/get operations
    - `RankingServiceImpl` → **mocked** ranking via `GameResultMapper.fromFields(...)`
- **Controllers** (if enabled):
    - `POST /game/new`, `GET /game/{id}`, `POST /game/{id}/play`, `DELETE /game/{id}/delete`
    - `GET /ranking`
    - `PUT /player/{playerId}`
- **Build**: project compiles; MapStruct implementations generated under `target/generated-sources/annotations/`

> The **real** ranking query/view in MySQL is scheduled for a later step (after Level 1). For now, the service returns mocked items.

---

## 🔧 Requirements

- **Java 21**
- **Maven 3.9+**
- **MongoDB 6+**
- **MySQL 8.x** (only needed if you enable Flyway and connect locally)

---

## ⚙️ Profiles & configuration

Planned profiles:
- **local**: local development (MySQL + Mongo on localhost).
- **docker**: containerized run (hostnames `mysql` and `mongo`).
- **test**: unit tests (Flyway disabled).
- **prod**: credentials via environment variables.

Example `src/main/resources/application.yml` (local):

```yaml
spring:
  r2dbc:
    url: r2dbc:pool:mysql://localhost:3306/blackjackdb
    username: root
    password: root
  flyway:
    url: jdbc:mysql://localhost:3306/blackjackdb
    user: root
    password: root

  data:
    mongodb:
      uri: mongodb://localhost:27017/blackjack

springdoc:
  swagger-ui:
    path: /swagger-ui.html
```

For **unit tests** (no external DBs), add `src/test/resources/application-test.yml`:
```yaml
spring:
  flyway:
    enabled: false
```

---

## ▶️ How to run

### 1) Clone
```bash
git clone https://github.com/anaberod/S05.01_BlackjackApi.git
cd S05.01_BlackjackApi
```

### 2) Build
```bash
./mvnw clean package -DskipTests
```

### 3) Run (profile **local**)
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### 4) Run with **Docker Compose** (if you have a compose for MySQL+Mongo)
```bash
docker compose up -d
./mvnw spring-boot:run -Dspring-boot.run.profiles=docker
```

---

## 🗄️ Migrations (Flyway)

Scripts will live under `src/main/resources/db/migration/`. Examples to be added later:
- `V1__init.sql` — base tables (players, etc.)
- `V2__ranking_view.sql` — `v_ranking` view (when implementing real ranking)

Flyway runs at startup if enabled and a MySQL connection is available.

---

## 📚 Swagger / OpenAPI

With the app running:
- **UI:** `http://localhost:8080/swagger-ui.html`
- **JSON:** `http://localhost:8080/v3/api-docs`

---

## 🔌 Endpoints (Level 1)

> All endpoints return **DTOs** (no entities/documents exposed).

- `POST   /game/new`  
  Creates a new game. **Body:** `CreateGameRequest { playerName }` → **201 Created** with `GameResponse`.

- `GET    /game/{id}`  
  Returns current game state → `GameResponse`.

- `POST   /game/{id}/play`  
  Performs an action. **Body:** `PlayRequest { action }` (HIT/STAND/DOUBLE/DEAL) → `GameResponse`.

- `DELETE /game/{id}/delete`  
  Deletes the game → **204 No Content**.

- `GET    /ranking`  
  Returns **mock** ranking → `Flux<RankingItem>` *(real MySQL-backed ranking will replace this later).*

- `PUT    /player/{playerId}`  
  Renames the player. **Body:** `PlayerRenameRequest { name }` → `PlayerView`.

---

## 🧪 Tests

```bash
./mvnw test
```

Included libs: `spring-boot-starter-test`, `reactor-test`, `mockito-core`, `mockito-junit-jupiter`.

Tips:
- Prefer **unit tests** for services using Mockito + StepVerifier (`@ExtendWith(MockitoExtension.class)`).
- For controllers, use **`@WebFluxTest`** (does not start external DBs).
- If you use `@SpringBootTest`, either run Mongo/MySQL locally or disable Flyway in the **test** profile.

---

## 🧩 Useful notes

- Enable **Annotation Processing** (Lombok + MapStruct) in your IDE.
- MapStruct mappers use `@Mapper(config = MapStructConfig.class)` → implementations generated in:  
  `target/generated-sources/annotations/cat/itacademy/blackjackapi/application/mapper/*Impl.java`
- `RankingServiceImpl` is **mocked** via `GameResultMapper.fromFields(...)`.  
  When integrating MySQL: create the `v_ranking` view + a R2DBC repository, then use `gameResultMapper.toRankingItem(...)`.

---

## 🗺️ Roadmap

- **Level 1 (current):** DTOs, mappers, services, controllers, global error handler, Swagger, unit tests.
- **Level 2:** Dockerization (`Dockerfile`, `.dockerignore`, optional `docker-compose` with Mongo + MySQL).
- **Level 3:** Deployment (Render / Docker Hub + GitHub Actions).
- **Post Level 1:** Real ranking persistence (Flyway `v_ranking`, R2DBC repository, service wiring).

---

## 📝 License

MIT (or your preferred license)
