# ADR-001: Kotlin 언어 선택

## 상태
Accepted

## 날짜
2026-05-19

## 컨텍스트

JVM 기반 백엔드 언어를 선택해야 한다. 주요 후보는 Java와 Kotlin이다.
이 프로젝트의 학습 목표(WebFlux/Reactive)와 코드 표현력을 동시에 고려한다.

## 결정

**Kotlin을 사용한다.**

## 근거

| 비교 항목 | Java | Kotlin | 결정 이유 |
|-----------|------|--------|-----------|
| Null Safety | `Optional<T>` 필요 | `T?` 내장 | 런타임 NPE 방지 |
| Data Class | Lombok 필요 | `data class` 내장 | 보일러플레이트 제거 |
| Coroutines 지원 | Project Reactor만 | `kotlinx.coroutines` + Reactor 병용 | 유연한 비동기 표현 |
| 함수형 표현 | 장황함 | 간결한 람다, 확장함수 | Reactor 체이닝 가독성 향상 |
| Spring 지원 | 1st-party | 공식 지원 (Spring Kotlin DSL) | 생태계 문제 없음 |

### Kotlin + WebFlux 시너지
- `Mono<T>`를 `suspend fun`으로 변환: `awaitSingle()`, `awaitFirstOrNull()`
- `Flux<T>`를 `Flow<T>`로 변환: `asFlow()`, `collect {}`
- Spring WebFlux의 Kotlin DSL 라우터: `coRouter { GET("/api") { } }`

## 결과

- build.gradle.kts(Kotlin DSL) 사용
- `kotlin-spring` 플러그인으로 `open` 클래스 자동 처리
- `kotlin-allopen`, `kotlin-noarg` 플러그인 적용 (JPA 대신 R2DBC지만 일관성 유지)

## 참조
- [Spring Boot Kotlin 가이드](https://spring.io/guides/tutorials/spring-boot-kotlin/)
- [Kotlin Coroutines + Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/languages.html#coroutines)
