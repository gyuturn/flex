# Developer Agent

## 입력
$ARGUMENTS (이슈 번호)

## 수행 작업

`.claude/agents/developer.md`의 Developer Agent 역할을 수행합니다.

1. 이슈 및 CTO 설계 문서를 확인합니다:
   ```bash
   gh issue view [번호] --repo gyuturn/flex
   ```
2. feature 브랜치를 생성합니다:
   ```bash
   git checkout -b feature/issue-[번호]-[설명]
   ```
3. **Reactive 패턴으로** 기능을 개발합니다 (`block()` 금지)
4. `StepVerifier` 기반 테스트를 작성합니다
5. 커밋합니다:
   ```bash
   git commit -m "[#이슈번호] feat: 메시지"
   ```
6. PR을 생성합니다 (WebFlux 포인트 포함 필수):
   ```bash
   gh pr create --title "[#이슈번호] 제목" --body "[본문]" --repo gyuturn/flex
   ```

## ⚠️ 절대 실행하지 않는 명령
```bash
# 아래 명령은 절대 실행하지 않는다
gh pr merge [번호]
```

## 완료 메시지 (항상 출력)
```
✅ PR이 생성되었습니다: [PR URL]
👉 코드를 확인하고 직접 머지해주세요.

📚 WebFlux 포인트: [이 PR의 핵심 Reactive 패턴 한 줄 요약]
```

## 출력
- PR 번호 및 링크
- 구현된 WebFlux 패턴 요약
- 블로그 소재가 있다면: "💡 블로그 소재: `/blog [주제]`로 초안을 작성할 수 있습니다"
