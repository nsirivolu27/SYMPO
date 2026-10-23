# SYMPO

SYMPO is a real-time sponsorship marketplace that helps sponsors discover compatible creators and review live marketplace activity. This monorepo includes:

- `matching-engine/`: Spring Boot microservice using Spring Data JPA for sponsor-creator matching
- `dashboard/`: React dashboard with live activity and match updates over WebSockets

## Highlights

- Attribute-based matching engine with weighted scoring
- Response-oriented API design for low-latency matching workflows
- Real-time activity feed delivered to the frontend over WebSockets
- Clean dashboard UI for managing creators, sponsors, and marketplace matches

## Architecture

```text
React Dashboard <-> WebSocket / REST
                     |
               Spring Boot API
                     |
                H2 or PostgreSQL
```

## Local Development

### Matching engine

```bash
cd matching-engine
./mvnw spring-boot:run
```

### Dashboard

```bash
cd dashboard
npm install
npm run dev
```

This project showcases:

- Spring Boot and Spring Data JPA
- Real-time matching logic under low latency constraints
- React dashboard design for operational workflows
- WebSocket-driven live activity updates

