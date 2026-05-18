# ADR-003: R2DBC + PostgreSQL 선택

## 상태
Accepted

## 날짜
2026-05-19

## 컨텍스트

Spring WebFlux를 선택한 이상, DB 드라이버도 Non-blocking이어야 한다.
JPA/JDBC는 내부적으로 스레드를 블로킹하므로 Reactive 스택과 함께 사용하면
`Schedulers.boundedElastic()`으로 오프로딩해야 하는 한계가 있다.

## 결정

**R2DBC + PostgreSQL을 사용한다.**

## 근거

### 블로킹 위험 제거
```kotlin
// ❌ JPA (블로킹 - Reactive 스택에서 위험)
val product = productRepository.findById(id) // 내부적으로 스레드 블로킹

// ✅ R2DBC (Non-blocking)
val product: Mono<Product> = productRepository.findById(id) // 비동기 반환
```

### Kotlin Coroutines 지원
Spring Data R2DBC는 `CoroutineCrudRepository`를 제공하여
`suspend fun` 기반 DB 접근이 가능하다:

```kotlin
interface ProductRepository : CoroutineCrudRepository<Product, Long> {
    suspend fun findByUrl(url: String): Product?
    fun findByUserId(userId: Long): Flow<Product>
}
```

### PostgreSQL 선택 이유
- R2DBC 공식 드라이버 성숙도가 가장 높음
- JSONB 타입 지원 (크롤링 메타데이터 저장)
- 프로덕션 신뢰성

## 트레이드오프

| 장점 | 단점 |
|------|------|
| 완전 Non-blocking DB 접근 | JPA 대비 기능 제한 (Lazy loading 없음) |
| Kotlin Coroutines 네이티브 지원 | 학습 곡선 |
| Reactor와 자연스러운 통합 | 복잡한 쿼리는 직접 SQL 작성 필요 |

## 마이그레이션 전략
- 초기: `CoroutineCrudRepository` 기반 단순 CRUD
- 복잡 쿼리: `@Query` 어노테이션 + 네이티브 SQL
- 스키마 관리: Flyway (R2DBC 호환 버전)

## 참조
- [Spring Data R2DBC](https://spring.io/projects/spring-data-r2dbc)
- [R2DBC PostgreSQL Driver](https://github.com/pgjdbc/r2dbc-postgresql)
