# Flex - 가격 추적기

실시간 상품 가격 추적 및 알림 서비스

## 기술 스택

- **Backend**: Spring WebFlux (Reactive)
- **Database**: R2DBC + PostgreSQL
- **알림**: SSE (Server-Sent Events), 카카오톡 / 슬랙 / 이메일
- **크롤링**: WebClient (비동기 병렬 처리)
- **빌드**: Gradle

## 핵심 기능

- 상품 URL 등록 후 주기적 가격 수집
- 목표가 도달 시 실시간 알림
- 가격 히스토리 차트
- 구독 플랜 (무료: 1주 히스토리 / 유료: 전체 히스토리 + 예측)

## 왜 WebFlux인가?

10,000개 URL을 주기적으로 크롤링하는 구조에서 블로킹 I/O는 스레드 고갈을 유발합니다.
WebFlux + WebClient로 이벤트 루프 기반 비동기 병렬 처리를 통해 메모리 사용량 1/10 수준으로 운영합니다.

## 로컬 실행

```bash
docker-compose up -d   # PostgreSQL 실행
./gradlew bootRun
```
