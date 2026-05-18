# ADR-004: Reactive(Mono/Flux) vs Kotlin Coroutines 혼용 전략

## 상태
Accepted

## 날짜
2026-05-19

## 컨텍스트

Kotlin + Spring WebFlux에서는 두 가지 비동기 프로그래밍 모델을 사용할 수 있다:

1. **Reactor 패턴**: `Mono<T>`, `Flux<T>` (Spring WebFlux 네이티브)
2. **Kotlin Coroutines**: `suspend fun`, `Flow<T>` (Kotlin 네이티브)

두 모델은 상호 변환이 가능하며, 이 프로젝트에서 어떤 전략으로 사용할지 결정한다.

## 결정

**Reactor(Mono/Flux)를 기본으로, Kotlin Coroutines를 계층별 보조 수단으로 사용한다.**

## 계층별 사용 전략

| 계층 | 사용 모델 | 이유 |
|------|-----------|------|
| **Presentation (Controller)** | `suspend fun` + `Flux<T>` | SSE는 Flux, 단건은 suspend가 가독성 우수 |
| **Application (Service)** | `Mono<T>` / `Flux<T>` | Reactor 연산자 체이닝으로 비즈니스 로직 표현 |
| **Infrastructure (Repository)** | `suspend fun` / `Flow<T>` | CoroutineCrudRepository 사용 |
| **Crawler** | `Flux<T>` + `flatMap` | 병렬 처리 동시성 제어 (`flatMap(concurrency=50)`) |

## 변환 패턴 (핵심)

```kotlin
// Coroutines → Reactor
suspend fun getProduct(): Product = ...
fun getProductMono(): Mono<Product> = mono { getProduct() }

// Reactor → Coroutines
fun getProductMono(): Mono<Product> = ...
suspend fun getProduct(): Product = getProductMono().awaitSingle()

// Flow ↔ Flux
fun productsFlow(): Flow<Product> = ...
fun productsFlux(): Flux<Product> = productsFlow().asFlux()
```

## 선택 이유

### Reactor 기본 사용 이유
- 이 프로젝트의 **학습 1순위**가 Reactor 패턴 이해이다
- `flatMap`, `zip`, `merge`, `onErrorResume` 등 연산자 직접 사용이 학습 가치 높음
- SSE Flux 스트림은 Reactor 방식이 더 직관적

### Coroutines 보조 사용 이유
- Repository 계층: `CoroutineCrudRepository`가 Spring Data 표준
- 간단한 단건 조회: `suspend fun`이 Mono보다 읽기 쉬움
- Kotlin 관용구와 일치

## 결과
- `kotlinx-coroutines-reactor` 의존성 필수 (상호 변환)
- Service 계층: Reactor 스타일 유지 (Mono/Flux 반환)
- Repository 계층: Coroutines 스타일 (suspend/Flow 반환)
- 블로그 소재: "Kotlin에서 Mono와 suspend fun 언제 쓸까?"

## 참조
- [Kotlin Coroutines + Reactor 공식 가이드](https://kotlinlang.org/docs/coroutines-and-channels.html)
- [kotlinx-coroutines-reactor](https://github.com/Kotlin/kotlinx.coroutines/tree/master/reactive/kotlinx-coroutines-reactor)
