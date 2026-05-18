# 시스템 아키텍처

## 전체 구조

```
[사용자] ─── HTTP/SSE ──► [Spring WebFlux API Server]
                                    │
                    ┌───────────────┼───────────────┐
                    ▼               ▼               ▼
              [R2DBC]        [WebClient]      [SSE Stream]
                    │               │               │
              [PostgreSQL]   [쇼핑몰 URL]    [알림 구독자]
```

## Reactive 스트림 흐름

### 크롤링 파이프라인

```
Flux.interval(10min)
  → flatMap { productRepository.findAll() }
  → flatMap(concurrency=50) { crawlerService.fetchPrice(url) }
  → filter { it.priceChanged }
  → flatMap { priceHistoryRepository.save(it) }
  → flatMap { notificationService.notify(it) }
  → onErrorResume { log.warn; Mono.empty() }
```

### SSE 알림 스트림

```
Client ──GET /api/v1/alerts/stream──► Controller
                                          │
                                    Flux<AlertEvent>
                                          │
                                    alertSink.asFlux()
                                          │
                                    [Sinks.Many<AlertEvent>]
                                    (Hot Publisher)
```

## 계층 설명

| 계층 | 역할 | 주요 기술 |
|------|------|-----------|
| **Presentation** | REST API, SSE 엔드포인트 | `@RestController`, `Flux<SSE>` |
| **Application** | 비즈니스 로직, 크롤링 조율 | `Mono<T>`, `Flux<T>`, `flatMap` |
| **Domain** | 엔티티, 도메인 규칙 | `data class`, R2DBC `@Table` |
| **Infrastructure** | DB, 외부 HTTP | `CoroutineCrudRepository`, `WebClient` |
| **Config** | 설정, 빈 등록 | `@Configuration`, `WebClient.Builder` |
