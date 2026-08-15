# ArenaHub
ArenaHub is a lightweight game backend service built with Spring Boot, focusing on player management, Redis caching, and real-time leaderboard scenarios.

MySQL is used as the primary storage for player data, while Redis is used for player caching and ZSet-based leaderboard ranking. When Redis is unavailable, basic player queries can fall back to MySQL, while Redis-dependent leaderboard APIs return a clear service unavailable response.

## Tech Stack

- Java 21
- Spring Boot
- MyBatis
- MySQL
- Redis
- Docker
- Maven

## Features

### Player Management

- Create, query, update, and delete player information.
- Store player data in MySQL through MyBatis.
- Return appropriate HTTP responses for invalid or missing players.

### Redis Cache

- Cache player information using Redis String.
- Apply the Cache Aside pattern for player queries.
- Set a TTL for cached player data.
- Invalidate cache entries after player updates or deletions.
- Fall back to MySQL when Redis is unavailable.

### Leaderboard

- Implement a global leaderboard using Redis ZSet.
- Increment player scores dynamically.
- Query Top N players in descending score order.
- Query an individual player's rank and score.
- Remove stale leaderboard members whose player records no longer exist.
- Return HTTP 503 when Redis-dependent leaderboard services are unavailable.

### Error Handling

- Use a custom `BusinessException` for business-level errors.
- Handle exceptions globally with `@RestControllerAdvice`.
- Return structured error responses with HTTP status codes, messages, and timestamps.

## Architecture

```text
Client
  |
  v
Controller
  |
  v
Service
  |
  +-------------------+
  |                   |
  v                   v
Redis              MyBatis
                      |
                      v
                    MySQL
```

The Controller layer handles HTTP requests and responses, while the Service layer contains the main business logic.

Player queries follow the Cache Aside pattern. Redis is checked first, and MySQL is queried through MyBatis when the cache is missing or unavailable.

The leaderboard is implemented with Redis ZSet to support score updates and ranking queries efficiently.

## API Endpoints

### Player APIs

| Method | Endpoint | Description |
|---|---|---|
| GET | `/players/{id}` | Get player information by ID |
| POST | `/players` | Create a new player |
| PUT | `/players/{id}/level` | Update a player's level |
| DELETE | `/players/{id}` | Delete a player |

Example request for creating a player:

```json
{
  "nickname": "player1"
}
```

Example request for updating a player's level:

```json
{
  "level": 10
}
```

### Leaderboard APIs

| Method | Endpoint | Description |
|---|---|---|
| POST | `/leaderboard/players/{id}/score` | Add score to a player |
| GET | `/leaderboard?limit={limit}` | Get the Top N leaderboard |
| GET | `/leaderboard/players/{id}` | Get a player's rank and score |

Example request for adding score:

```json
{
  "deltaScore": 50
}
```

## Error Handling and Redis Fallback

ArenaHub uses centralized exception handling to provide consistent HTTP error responses.

Business-related exceptions are handled through `@RestControllerAdvice` and returned with a structured response containing an HTTP status code, error message, and timestamp.

Example error response:

```json
{
  "code": 404,
  "message": "Player not found",
  "timestamp": 1786786331582
}
```

For player queries, Redis is treated as a cache layer. If Redis is unavailable, the service falls back to MySQL so that basic player queries can continue to work.

For leaderboard APIs that depend on Redis ZSet, a Redis connection failure returns HTTP `503 Service Unavailable` instead of an internal server error.

## Getting Started

### Prerequisites

- JDK 21+
- Maven
- MySQL
- Docker

### Configuration

Create a MySQL database named `arenahub` and configure the connection in `application.properties`.

The database password is provided through the `DB_PASSWORD` environment variable.

Start Redis with Docker:

```bash
docker start arenahub-redis
```

If the Redis container has not been created yet:

```bash
docker run --name arenahub-redis \
  -p 127.0.0.1:6379:6379 \
  -d redis:8.2
```

Start the application:

```bash
mvn spring-boot:run
```

The service runs at:

```text
http://localhost:8080
```

## Database Schema

ArenaHub currently uses a simple `player` table to store core player information.

```sql
CREATE TABLE player (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nickname VARCHAR(64) NOT NULL,
    level INT NOT NULL DEFAULT 1
);
```

The `id` field is used as the stable player identifier across MySQL and Redis.