# ADR-002: Spring WebFlux 선택

## 상태
Accepted

## 날짜
2026-05-19

## 컨텍스트

가격 추적 서비스는 다음과 같은 특성을 가진다:
- 수백~수천 개 상품 URL을 주기적으로 크롤링 (I/O bound)
- 가격 변동 시 SSE(Server-Sent Events)로 실시간 알림 전송
- 다수의 동시 WebClient 요청 처리

Spring MVC(Servlet/Blocking) vs Spring WebFlux(Reactive/Non-blocking) 선택이 필요하다.

## 결정

**Spring WebFlux를 사용한다.**

## 근거

### 크롤링 특성 분석
```
[크롤링 요청 N개]
  → Thread per request (MVC): N 스레드 점유, 대기 중 블로킹
  → Reactive (WebFlux): 적은 스레드로 N개 비동기 처리, 이벤트 루프 활용
```

가격 추적 특성상 **대부분의 시간이 I/O 대기**이므로 Non-blocking이 유리하다.

### SSE 실시간 알림
- Spring MVC: 비동기 응답을 위해 별도 처리 필요
- Spring WebFlux: `Flux<ServerSentEvent<T>>`로 자연스럽게 스트림 반환

### 학습 목표 부합
이 프로젝트의 1순위 목표는 WebFlux 학습이다.
실서비스 요구와 학습 목표가 일치하는 유일한 선택이다.

## 트레이드오프

| 장점 | 단점 |
|------|------|
| I/O bound 작업 효율적 처리 | 학습 곡선 (Reactor 패턴) |
| SSE 스트림 자연 지원 | 블로킹 라이브러리 사용 불가 |
| 적은 스레드로 높은 처리량 | 디버깅/스택트레이스 복잡 |
| Kotlin Coroutines와 호환 | 생태계 일부 미지원 |

## 결과
- `spring-boot-starter-webflux` 사용
- Netty 임베디드 서버 (Tomcat 대신)
- `block()` 호출 원칙적 금지

## 참조
- [Spring WebFlux 공식 문서](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
