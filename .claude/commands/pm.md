# PM Agent

## 입력
$ARGUMENTS

## 수행 작업

`.claude/agents/pm.md`의 PM Agent 역할을 수행합니다.

1. 입력된 요구사항을 분석합니다
2. **학습 가치** (어떤 WebFlux 패턴이 활용되는가)와 **수익 가치** (유료/무료 플랜 연결) 동시 평가
3. 기능 단위로 분해합니다
4. GitHub 이슈를 생성합니다:
   ```bash
   gh issue create --title "[제목]" --body "[본문]" --label "[라벨]" --repo gyuturn/flex
   ```
5. 생성된 이슈 번호와 링크를 출력합니다

## 출력
- 생성된 이슈 번호 및 링크
- 학습 포인트 요약 (어떤 WebFlux 패턴이 필요한가)
- 다음 단계 안내: `/cto [이슈번호]`
