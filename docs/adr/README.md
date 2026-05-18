# ADR (Architecture Decision Records)

## 개요

이 디렉토리는 프로젝트의 **아키텍처 결정 이력**을 기록합니다.
ADR은 "왜 이 기술/구조를 선택했는가"를 미래의 나(또는 팀원)에게 설명하는 문서입니다.

## ADR 목록

| 번호 | 제목 | 상태 |
|------|------|------|
| [ADR-001](ADR-001-kotlin-language.md) | Kotlin 언어 선택 | ✅ Accepted |
| [ADR-002](ADR-002-spring-webflux.md) | Spring WebFlux 선택 | ✅ Accepted |
| [ADR-003](ADR-003-r2dbc-postgresql.md) | R2DBC + PostgreSQL 선택 | ✅ Accepted |
| [ADR-004](ADR-004-reactive-vs-coroutines.md) | Reactive vs Coroutines 혼용 전략 | ✅ Accepted |

## ADR 작성 방법

### 새 ADR 추가
1. `ADR-NNN-[주제].md` 파일 생성
2. 아래 템플릿 사용
3. 이 README 목록 업데이트
4. CLAUDE.md의 ADR 섹션에 요약 추가

### ADR 상태

| 상태 | 의미 |
|------|------|
| `Proposed` | 제안됨, 아직 결정 안됨 |
| `Accepted` | 채택됨, 현재 적용 중 |
| `Deprecated` | 더 이상 유효하지 않음 |
| `Superseded by ADR-NNN` | 다른 ADR로 대체됨 |

### ADR 템플릿

```markdown
# ADR-NNN: [제목]

## 상태
Proposed / Accepted / Deprecated / Superseded by ADR-NNN

## 날짜
YYYY-MM-DD

## 컨텍스트
[이 결정이 필요한 배경 상황]

## 결정
[무엇을 결정했는가]

## 근거
[왜 이 결정을 내렸는가 - 트레이드오프 포함]

## 결과
[이 결정이 미치는 영향]

## 참조
[관련 문서, 링크]
```

## 언제 ADR을 작성하는가

- 새로운 라이브러리/프레임워크를 도입할 때
- 아키텍처 패턴을 변경할 때
- 기존 결정을 번복할 때
- "왜 이렇게 했지?"라는 질문이 나올 것 같을 때
