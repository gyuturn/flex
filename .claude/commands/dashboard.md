# Dashboard

프로젝트 현황 대시보드를 출력합니다.

## 수행 작업

다음 정보를 순서대로 조회하고 출력합니다:

### 1. GitHub Issues 현황
```bash
gh issue list --repo gyuturn/flex --state open
```

### 2. Pull Requests 현황
```bash
gh pr list --repo gyuturn/flex
```

### 3. Git 상태
```bash
git log --oneline -5
git branch
```

### 4. 블로그 초안 현황
```bash
ls docs/blog/ 2>/dev/null || echo "초안 없음"
```

## 출력 형식

```
=== Flex 프로젝트 대시보드 ===

📋 Open Issues ([n]개)
[이슈 목록]

🔀 Pull Requests ([n]개)
[PR 목록 - 머지 대기 중인 PR 강조]

📝 최근 커밋
[git log 결과]

📄 블로그 초안
[docs/blog/ 파일 목록]
```
