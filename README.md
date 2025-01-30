![header](https://capsule-render.vercel.app/api?type=wave&color=auto&height=300&section=header&text=Yoolog&fontSize=70)

# 2025 동계 방학 나의 첫 웹서비스 캠프 Final Project
# Velog 클론 코딩
## 주제: 각자의 캡스톤 프로젝트와 연관된 기능을 구현해보자!
25-1 학기에 캡스톤디자인2 를 수강하는 랩실 학생들과 스터디 그룹을 만들어 각자 이번 방학과 다음 학기 중에 진행할 졸업 프로젝트에서 필요한 기능들을 이 캠프 기간 동안 연습해볼 것을 결정하였음

### 나의 캡스톤 프로젝트 주제: SW 마일스톤 관리 서비스 개발

## 연습해야 할 기능
_체크된 항목은 해당 프로젝트에서 연습해본 기능을 의미함_
- [x] Multipart 를 이용한 파일 업로드 기능: 학생들의 정보가 유출되면 안되므로 `S3` 와 같은 클라우드 서비스 사용 불가
- [x] 신입 개발자에게 필요한 역량 별로 항목(학생들의 활동 내역)을 카테고리화하는 기능
- [ ] 학교 홈페이지 로그인 OAuth 연결

## Tech Stack
`SpringBoot` `Docker` `Thymeleaf` `HTML` `CSS` `Javascript`

## ER Diagram
<img width="697" alt="Image" src="https://github.com/user-attachments/assets/c95803d6-f078-45e3-aa07-aa0cc7eb08f6" />

## 개발 중에 작성한 기술 블로그
[<img align="left" alt="yoomin-hwang | velog" width="48px" src="https://img.icons8.com/color/48/000000/blog.png" />][website]

[website]: https://velog.io/@peanuts/Docker-%EB%A5%BC-%ED%86%B5%ED%95%B4-MariaDB-%EC%8B%A4%ED%96%89-%EB%B0%8F-DBeaver-%EC%A0%91%EC%86%8D%ED%95%98%EA%B8%B0
    Docker 를 활용한 WEB 프로젝트 실행 방법
추가 예정


## 배포 링크와 시연 영상
추가 예정

## 회고 (2025.01.25)
- 학교 계정 로그인 기능을 구현하다가 포기한 부분이 아쉬움
- 파일 업로드와 카테고리 할당 부분에서 CR 기능들은 잘 구현헀지만 시간이 촉박하여 UD 기능이 잘 되지 않는 상태로 배포한 것이 아쉬움
- 지난 프로젝트들의 앞단은 모두 React 와 매핑된 API 를 이용해서 개발해왔는데, 이번 기회에 Thymeleaf 를 사용해볼 수 있어 유익했음
- 누군가의 도움 없이 나 혼자 단순 CRUD 이상의 벡엔드를 개발해본 것은 처음이라 많이 미숙하지만 이 경험을 바탕으로 더 많이 성장하고 싶음
- 위에 언급한 아쉬운 점들은 남은 방학 기간 동안 차차 보완해 나가도록 하겠음