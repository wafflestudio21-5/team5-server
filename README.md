
# 🧇 Waffle5gram
## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [배포 링크](#배포-링크)
3. [팀원 구성](#팀원-구성)
4. [프로젝트 뷰](#프로젝트-뷰)
5. [사용 기술](#사용-기술)
6. [서버 구성도](#서버-구성도)
7. [ERD](#erd)
8. [사용한 컨벤션](#사용한-컨벤션)
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

## 프로젝트 뷰
- 로그인, 홈 피드
<img width="476" alt="로그인" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/ef1ba1a1-08a1-45c7-b06f-c11b32d4f800">
<img width="494" alt="홈 피드" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/b41c2bd0-82d6-4209-8797-a5f96f2299c3">


- 카테고리별 탐색, 인물 검색
<img width="485" alt="카테고리 탐색 탭" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/f7173726-b10a-4ee9-baa8-ca1d9b5f7e56">
<img width="431" alt="인물 검색" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/852503c7-a379-4d9f-83c2-bc99fe50db79">


- 게시물 업로드, 사용자 프로필
<img width="501" alt="게시물 생성" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/8eca4838-5457-482d-9452-afabc0ce43d1">
<img width="483" alt="프로필" src="https://github.com/wafflestudio21-5/team5-server/assets/87258768/f04e7488-b8ed-4794-a44c-13778f5fac48">


## 사용 기술
![Static Badge](https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge&logo=Kotlin&logoColor=white) ![Static Badge](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white&labelColor=%236DB33F) ![Static Badge](https://img.shields.io/badge/springboot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white&labelColor=%236DB33F) ![Static Badge](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white&labelColor=%236DB33F)
![Static Badge](https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white&labelColor=%23009639) ![Static Badge](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white&labelColor=%234479A1)  ![Static Badge](https://img.shields.io/badge/amazon%20ec2-%23FF9900.svg?style=for-the-badge&logo=amazonec2&logoColor=white&labelColor=%23FF9900) ![Static Badge](https://img.shields.io/badge/docker-%232496ED.svg?style=for-the-badge&logo=docker&logoColor=white&labelColor=%232496ED)

## 서버 구성도
![server](https://github.com/wafflestudio21-5/team5-server/assets/102405643/3230dfb6-303a-48c7-ae3d-8036464a31e2)


## ERD
![Waffle5gram_ERD_v2-4](https://github.com/wafflestudio21-5/team5-server/assets/87258768/f23e4fc4-2bae-4455-bb66-6fcdd1750c3e)


## 사용한 컨벤션

### Code Convention
- 팀원 간 코드 스타일을 맞추고 가독성을 높여 코드 리뷰를 원활하게 하기 위해 ktlint를 사용하였습니다.
- 제일 많이 쓰이는 jlleitschuh/ktlint-gradle을 사용하였습니다.
- Github Action 을 이용하여 main 브랜치에 PR 올릴때마다 자동으로 스타일 검사를 하도록 설정하였습니다.
### Commit Convention
- 아래와 같은 커밋 컨벤션을 통해 서로의 작업물이 어떤 유형의 것인지 파악하기 쉽도록 하였습니다.

### Git Branch & merge 전략
- 토이 프로젝트의 성격을 고려하여 간단한 Branching 전략인 Github-flow를 사용했습니다.
- 또한, main 브랜치의 중요도를 고려하여 깔끔한 history를 위해 Squash Merge를 사용했습니다.
