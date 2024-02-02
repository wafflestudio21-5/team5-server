
# 🧇 Waffle5gram
## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [배포 링크](#배포-링크)
3. [팀원 구성](#팀원-구성)
4. [서비스 소개 페이지](#서비스-소개-페이지)
5. [사용 기술](#사용-기술)
6. [서버 구성도](#서버-구성도)
7. [사용한 컨벤션](#사용한-컨벤션)
---
## 프로젝트 소개
- 2023 와플스튜디오 Rookies 세미나에서 학습한 내용을 토대로 널리 사용되는 SNS인 인스타그램 서비스를 클론 코딩하는 프로젝트의 API 서버입니다.
- 프로젝트 기간과 목적을 고려하여 다음과 같은 필수 스펙을 우선적으로 구현하였습니다.
- [X] 회원가입 / 로그인 / 소셜 로그인
- [x] 유저 계정 페이지
- [x] 글 작성 / 댓글 작성
- [x] 페이지네이션
- [x] AWS 배포
- [x] HTTPS 설정
- [x] GITHUB Actions CI/CD
- 또한 기존에 없던 새로운 기능으로 검색 파트에서 인물 검색과 게시물 검색을 분리, 카테고리를 바탕으로 피드를 생성해주는 탭을 추가하였습니다.
- 이외에 조금 더 비슷한 구현을 위해 좋아요 기능, 게시물 저장 기능, 피드 기능, 팔로우 기능, 검색 기능을 추가하였습니다.
## 팀원 구성
<table>  <tr>  <td></td> <td>이희승(Leader)</td> <td>이수혁</td> <td>조성해</td> <td>김지원</td> </tr> <tr> <td>GitHub</td> <td><a href="https://github.com/rayark1"><img src="https://avatars.githubusercontent.com/u/102405643?v=4" width="100"></a></td> <td><a href="https://github.com/isuh88"><img src="https://avatars.githubusercontent.com/u/105275625?v=4" width="100"></a></td> <td><a href="https://github.com/SeonghaeJo"><img src="https://avatars.githubusercontent.com/u/87258768?v=4" width="100"></a></td> <td><a href="https://github.com/jj1kim"><img src="https://avatars.githubusercontent.com/u/134778013?v=4" width="100"></a></td> </tr> </table>

## 배포 링크
- 배포링크 : https://api.waffle5gram.com
## 서비스 소개 페이지
![Notion Link](https://ancient-vinyl-ddb.notion.site/a2b526a9c9a84c8da75b6d403cbbb410?pvs=4)
## 사용 기술
![Static Badge](https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=Kotlin&logoColor=white) ![Static Badge](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F) ![Static Badge](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=%236DB33F) ![Static Badge](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white&labelColor=%236DB33F)
![Static Badge](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white&labelColor=%23009639) ![Static Badge](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white&labelColor=%234479A1)  ![Static Badge](https://img.shields.io/badge/amazon%20ec2-%23FF9900.svg?style=for-the-badge&logo=amazonec2&logoColor=white&labelColor=%23FF9900) ![Static Badge](https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white&labelColor=%232496ED)

## 서버 구성도
![server](https://user-images.githubusercontent.com/72662822/216547325-5e281374-e3f4-405d-aa8c-4ac6b1c018c4.png)

## 사용한 컨벤션

### Code Convention
- 팀원 간 코드 스타일을 맞추고 가독성을 높여 코드 리뷰를 원할하게 하기 위해 ktlint를 사용하였습니다.
- 제일 많이 쓰이는 jlleitschuh/ktlint-gradle을 사용하였습니다.
- Github Action 을 이용하여 main 브랜치에 PR 올릴때마다 자동으로 스타일 검사를 하도록 설정하였습니다.
### Commit Convention
- 아래와 같은 커밋 컨벤션을 통해 서로의 작업물이 어떤 유형의 것인지 파악하기 쉽도록 하였습니다.

### Git Branch & merge 전략
- 토이 프로젝트의 성격을 고려하여 간단한 Branching 전략인 Github-flow를 사용했습니다.
- 또한, main 브랜치의 중요도를 고려하여 깔끔한 history를 위해 Squash Merge를 사용했습니다.

