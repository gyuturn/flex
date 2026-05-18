# CLAUDE.md — flex

## 프로젝트 개요

실시간 상품 가격 추적 및 알림 서비스.
유저가 상품 URL을 등록하면 주기적으로 크롤링하여 목표가 도달 시 알림을 발송한다.

- **Repository**: https://github.com/gyuturn/flex

---

## 프로젝트 철학

> "WebFlux를 쓰기 위해 이 프로젝트를 하는 게 아니라,
> 이 프로젝트를 제대로 만들려면 WebFlux를 써야 하는 구조다."

### 이중 목표

| 목표 | 내용 |
|------|------|
| **학습 (1순위)** | Spring WebFlux / Non-blocking I/O / Reactor 패턴의 깊은 이해 |
| **수익 (2순위)** | 실제 사용자 트래픽 유치 + 구독 플랜을 통한 수익 실현 |

두 목표는 충돌하지 않는다. 제대로 만들어야 사용자도 쓴다.

### 개발 원칙

- **Reactive First**: 블로킹 코드는 작성하지 않는다. `block()` 호출은 명시적 사유 없이 금지
- **학습 가시화**: 의미있는 WebFlux 패턴이 등장하면 PR description에 개념 설명을 포함한다
- **유저 승인 머지**: **PR은 반드시 유저(나)가 직접 검토 후 머지한다. 자동 머지 절대 금지**
- **블로그 소재 발굴**: `flatMap`, `zip`, `SSE`, `R2DBC` 등 설명할 만한 패턴은 `/blog`로 초안 작성
- **구독 가치 우선**: 기능 추가 전 "이게 유료 플랜의 근거가 되는가?" 를 먼저 묻는다

### 기능 추가 기준

새로운 기능을 제안할 때 다음 질문에 답한다:
1. 이 기능에서 **WebFlux를 쓸 이유**가 있는가? (학습 가치)
2. 이 기능이 **유료 전환의 근거**가 되는가? (수익 가치)
3. 이 기능이 없어도 핵심 서비스가 동작하는가? (우선순위)

---

## 기술 스택

| 영역 | 기술 | 버전 | ADR |
|------|------|------|-----|
| **언어** | Kotlin | 1.9.x | [ADR-001](docs/adr/ADR-001-kotlin-language.md) |
| **Runtime** | JVM 21 | LTS | - |
| **Backend** | Spring Boot + WebFlux | 3.3.x | [ADR-002](docs/adr/ADR-002-spring-webflux.md) |
| **Reactive** | Project Reactor (Mono / Flux) | Reactor Core 3.x | [ADR-004](docs/adr/ADR-004-reactive-vs-coroutines.md) |
| **Coroutines** | Kotlin Coroutines + Reactor Bridge | 1.8.x | [ADR-004](docs/adr/ADR-004-reactive-vs-coroutines.md) |
| **HTTP Client** | WebClient (비동기 크롤링) | Spring 내장 | - |
| **Database** | R2DBC + PostgreSQL | - | [ADR-003](docs/adr/ADR-003-r2dbc-postgresql.md) |
| **DB Migration** | Flyway | 10.x | - |
| **실시간 알림** | SSE (Server-Sent Events) | - | - |
| **외부 알림** | 카카오톡 / 슬랙 / 이메일 | - | - |
| **스케줄러** | Spring Scheduler + Reactive | - | - |
| **빌드** | Gradle (Kotlin DSL) | 8.x | - |
| **CI/CD** | GitHub Actions | - | - |

---

## 에이전트 구조

| 커맨드 | 역할 |
|--------|------|
| `/pm [요구사항]` | 요구사항 분석 + GitHub 이슈 생성 |
| `/cto [이슈번호]` | 기술 검토 + WebFlux 설계 + `/docs`에 문서 작성 |
| `/developer [이슈번호]` | 브랜치 생성 + 개발 + PR 생성 (머지 금지) |
| `/blog [주제/이슈번호]` | WebFlux 관련 블로그 포스트 초안 생성 (`/docs/blog/`) |
| `/feature [요구사항]` | PM → CTO → Developer 전체 파이프라인 자동 실행 |

---

## 커밋 컨벤션

```
[#이슈번호] 타입: 메시지

타입: feat / fix / refactor / docs / test / chore
```

## 브랜치 전략

- `feature/issue-[번호]-[설명]`
- `bugfix/issue-[번호]-[설명]`
- `hotfix/issue-[번호]-[설명]`

---

## PR 규칙 (중요)

### ⚠️ PR은 유저가 직접 머지한다

- Claude(에이전트)는 **PR을 생성**하는 것까지만 수행한다
- `gh pr merge` 명령은 **절대 실행하지 않는다**
- PR 생성 후 반드시 유저에게 리뷰 요청 메시지를 출력한다:
  ```
  ✅ PR이 생성되었습니다: [PR URL]
  👉 코드를 확인하고 직접 머지해주세요.
  ```

### PR Description 필수 항목

```markdown
## 구현 내용
[기능 설명]

## WebFlux 포인트
[이 PR에서 사용된 Reactive 패턴 설명 - 학습 목적]
- 예: flatMap vs concatMap 선택 이유
- 예: Scheduler.boundedElastic() 사용 이유

## 테스트
- [ ] 단위 테스트
- [ ] 통합 테스트

Closes #[이슈번호]
```

---

## 이슈 관리

- PR 머지(유저가 직접) 후 해당 이슈 close
- `gh issue close [번호] --repo gyuturn/flex --comment "PR #[번호]에서 구현 완료"`
- 세션 시작 시 이미 구현 완료된 open 이슈 확인 후 정리

---

## 코드 컨벤션

### Kotlin 스타일
- Kotlin 공식 스타일 가이드 준수 (ktlint 기준)
- 클래스명: `PascalCase`, 함수명: `camelCase`, 상수: `UPPER_SNAKE_CASE`
- `data class` 적극 활용 (불변 도메인 객체)
- `?` nullable 타입 최소화 — 불가피한 경우에만 사용

### Reactive 코드 컨벤션

| 계층 | 반환 타입 | 규칙 |
|------|-----------|------|
| **Controller** | `suspend fun` / `Flow<T>` | 단건은 suspend, 스트림은 Flow |
| **Service** | `Mono<T>` / `Flux<T>` | Reactor 연산자 체이닝 중심 |
| **Repository** | `suspend fun` / `Flow<T>` | CoroutineCrudRepository 표준 |

```kotlin
// ✅ 좋은 예
fun findActiveProducts(): Flux<Product> = ...
suspend fun findById(id: Long): Product? = ...

// ❌ 나쁜 예
fun findActiveProducts(): List<Product> = ...  // 블로킹
fun anything(): Mono<T> = mono { }.block()     // block() 금지
```

### 커밋 컨벤션
```
[#이슈번호] 타입: 메시지

타입: feat / fix / refactor / docs / test / chore
```

---

## ADR (Architecture Decision Records) 관리

모든 아키텍처 결정은 `/docs/adr/`에 ADR 문서로 기록한다.

### ADR 목록

| ADR | 제목 | 상태 |
|-----|------|------|
| [ADR-001](docs/adr/ADR-001-kotlin-language.md) | Kotlin 언어 선택 | Accepted |
| [ADR-002](docs/adr/ADR-002-spring-webflux.md) | Spring WebFlux 선택 | Accepted |
| [ADR-003](docs/adr/ADR-003-r2dbc-postgresql.md) | R2DBC + PostgreSQL 선택 | Accepted |
| [ADR-004](docs/adr/ADR-004-reactive-vs-coroutines.md) | Reactive vs Coroutines 혼용 전략 | Accepted |

### ADR 작성 시점
- 새 라이브러리/프레임워크 도입 시
- 아키텍처 패턴 변경 시
- "왜 이렇게 했지?"가 나올 것 같을 때

자세한 ADR 작성 방법: [docs/adr/README.md](docs/adr/README.md)

---

## 시스템 아키텍처 (Big Picture)

```
[사용자 브라우저]
      │
      │ HTTP REST + SSE
      ▼
┌─────────────────────────────────────────┐
│         Spring WebFlux API Server        │
│  (Netty, Non-blocking, Event Loop)       │
│                                          │
│  ┌──────────┐  ┌──────────┐  ┌────────┐ │
│  │Presentat-│  │Applicat- │  │Domain  │ │
│  │ion Layer │→ │ion Layer │→ │Entities│ │
│  │(Router/  │  │(Reactor  │  │(data   │ │
│  │Controller│  │Mono/Flux)│  │class)  │ │
│  └──────────┘  └──────────┘  └────────┘ │
│                     │                    │
│        ┌────────────┼───────────┐        │
│        ▼            ▼           ▼        │
│  ┌──────────┐ ┌──────────┐ ┌────────┐   │
│  │R2DBC     │ │WebClient │ │Sinks   │   │
│  │Repository│ │(Crawler) │ │(SSE)   │   │
│  └──────────┘ └──────────┘ └────────┘   │
└─────────────────────────────────────────┘
        │               │
        ▼               ▼
  [PostgreSQL]    [쇼핑몰 URL들]
  (Non-blocking   (병렬 크롤링
   via R2DBC)      flatMap 50)
```

### 핵심 Reactive 흐름

**크롤링 파이프라인** (10분 주기):
```
Scheduler → findByActiveTrue() [Flow] → toFlux()
  → flatMap(50) { fetchPrice(url) }    # 최대 50개 동시 WebClient 요청
  → filter { price != null }
  → flatMap { priceHistoryRepository.save(history) }
  → onErrorResume { log.warn; Mono.empty() }  # 일부 실패 허용
```

**SSE 알림 스트림**:
```
Client ──GET /api/v1/alerts/stream──►
  Sinks.many().multicast() [Hot Publisher]
    → alertSink.asFlux()
    → map { ServerSentEvent.builder(it).build() }
    → 구독자에게 브로드캐스트
```

---

## 디렉토리 구조

```
/
├── src/
│   └── main/kotlin/com/flex/
│       ├── FlexApplication.kt          # 진입점
│       ├── domain/                     # 도메인 엔티티 (data class + @Table)
│       │   ├── Product.kt
│       │   ├── PriceHistory.kt
│       │   └── PriceAlert.kt
│       ├── application/                # 서비스 레이어 (Mono/Flux 반환)
│       │   ├── tracker/                # 가격 추적 로직
│       │   ├── notification/           # SSE + 외부 알림
│       │   └── crawler/                # WebClient 크롤링
│       ├── infrastructure/             # R2DBC Repository, WebClient
│       │   └── repository/
│       ├── presentation/               # @RestController (suspend + Flow + SSE)
│       └── config/                     # WebFlux, R2DBC, WebClient 설정
├── docs/
│   ├── adr/                            # Architecture Decision Records
│   ├── architecture/                   # 시스템 설계 문서
│   ├── api/                            # API 설계
│   └── blog/                           # WebFlux 블로그 포스트 초안
├── src/main/resources/
│   ├── application.yml
│   └── db/migration/                   # Flyway SQL 마이그레이션
└── .github/
    └── workflows/
```
