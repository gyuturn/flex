# Feature Pipeline

기능 요구사항을 받아 PM → CTO → Developer 순서로 전체 파이프라인을 자동 실행합니다.

## 입력
$ARGUMENTS

## 실행 순서

다음 순서로 각 에이전트를 순차적으로 실행하세요:

### 1. PM Agent
`.claude/agents/pm.md` 참고
1. 요구사항을 분석합니다
2. 학습 가치 + 수익 가치 평가
3. 기능을 분해합니다
4. GitHub 이슈를 생성합니다 (`gh issue create --repo gyuturn/flex`)
5. 생성된 이슈 번호를 기록합니다

### 2. CTO Agent
`.claude/agents/cto.md` 참고
1. PM이 생성한 이슈를 확인합니다
2. WebFlux 관점에서 Reactive 설계를 수행합니다
3. Reactor 연산자 선택 및 근거를 정리합니다
4. `/docs/architecture/`에 설계 문서를 작성합니다
5. 이슈에 기술 검토 의견을 댓글로 추가합니다

### 3. Developer Agent
`.claude/agents/developer.md` 참고
1. 이슈, 설계 문서를 확인합니다
2. feature 브랜치를 생성합니다
3. **Reactive 패턴으로** 기능을 개발합니다
4. StepVerifier 테스트를 작성합니다
5. PR을 생성합니다 (WebFlux 포인트 포함 필수)
6. **절대 머지하지 않습니다**

## 참조 문서
- PM: `.claude/agents/pm.md`
- CTO: `.claude/agents/cto.md`
- Developer: `.claude/agents/developer.md`

## 출력 (각 단계 완료 후)
- **PM**: 생성된 이슈 번호 및 링크
- **CTO**: 기술 설계 요약 + 사용할 핵심 Reactor 연산자
- **Developer**: PR 번호 및 링크

## 최종 완료 메시지 (항상 출력)
```
✅ PR이 생성되었습니다: [PR URL]
👉 코드를 확인하고 직접 머지해주세요.

📚 WebFlux 포인트: [이 PR의 핵심 Reactive 패턴]
💡 블로그 소재: [있다면 제안]
```
