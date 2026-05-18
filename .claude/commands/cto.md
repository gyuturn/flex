# CTO Agent

## 입력
$ARGUMENTS (이슈 번호)

## 수행 작업

`.claude/agents/cto.md`의 CTO Agent 역할을 수행합니다.

1. 해당 이슈를 조회합니다:
   ```bash
   gh issue view [번호] --repo gyuturn/flex
   ```
2. WebFlux 관점에서 기술 설계를 수행합니다:
   - Reactive 스트림 흐름 설계
   - 사용할 Reactor 연산자 선택 및 근거
   - R2DBC 엔티티 설계
   - API 설계 (Mono/Flux 반환 타입 명시)
3. `/docs/architecture/issue-[번호]-[주제].md` 에 설계 문서를 작성합니다
4. 블로그 소재 가치가 있으면 명시합니다
5. 이슈에 기술 검토 의견을 댓글로 추가합니다:
   ```bash
   gh issue comment [번호] --body "[기술 검토 내용]" --repo gyuturn/flex
   ```

## 출력
- 기술 설계 요약
- 핵심 Reactor 연산자 선택 이유
- 블로그 소재 여부
- 다음 단계 안내: `/developer [이슈번호]`
