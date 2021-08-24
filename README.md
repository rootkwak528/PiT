![issue](https://img.shields.io/badge/issue-0-yellow.svg) ![ubuntu](https://img.shields.io/badge/ubuntu-20.04-important.svg)<br />
![openvidu](https://img.shields.io/badge/openvidu-2.19.0-brightgreen.svg) ![spring](https://img.shields.io/badge/spring-2.5.4-brightgreen.svg) ![node](https://img.shields.io/badge/node-14.16.1-brightgreen.svg) ![vue](https://img.shields.io/badge/vue-3.0.0-brightgreen.svg)<br />
![vuex](https://img.shields.io/badge/vuex-4.0.0-green.svg) ![element-plus](https://img.shields.io/badge/element_plus-1.1.0_beta.1-green.svg) ![bootstrap](https://img.shields.io/badge/bootstrap-5.1.0-green.svg)

<br />

# P;T 피땀눈물

## 목차

[1. P;T 소개](#1-PT-소개)<br/>
[2. 개요](#2-개요)<br/>
[3. 기술 스택](#3-기술-스택)<br/>
[4. 형상 관리](#4-형상-관리)<br/>
[5. 주기능 소개](#5-주기능-소개)<br/>
[6. 서버 구조](#6-서버-구조)<br/>
[7. 데이터베이스 구조](#7-데이터베이스-구조)<br/>
[8. 실행 방법](#8-실행-방법)<br/>
[9. Contributor](#9-contributor)<br/>

<br />
<br />

## 1. P;T 소개

- 개발기간 : 2021.07.12 - 2021.08.20
- 개발인원 : 곽호근, 서민영, 서요셉, 안대현, 윤영은
- 주제 : 화상 PT와 클래스 신청 서비스를 연계한 통합 웹 서비스
- 역할<br />
  ![overview - role and responsibilities](https://i.imgur.com/sMBvMyA.png)
    - Backend : 서요셉, 안대현
    - Frontend : 서요셉, 윤영은
    - WebRTC : 곽호근, 서민영

<br />
<br />

## 2. 개요

홈트도 **😎 전문가**와 함께 하세요!

> 🥱 바쁘다바빠! 현대사회와 😷 COVID19로 인해 **언택트 라이프**가 일상에 자리잡았습니다.
>
> P;T는 코로나 감염 우려로 헬스장에 가지 못하는 사용자들을 위해 화상미팅 기능을 이용한 **PT 서비스**와 클래스를 탐색해 신청하고 관리할 수 있는 **통합 웹 사이트**를 제공하는 서비스입니다.
>
> **여러가지 운동** 카테고리를 제공하여 클래스 탐색을 도와줍니다. **일대일, 일대다 수업**으로 전문적인 코칭은 물론 모두 함께 수업하며 운동 시너지를 낼 수 있습니다.

ㅤ

### P;T 기획 배경

![overview - project background 1](https://i.imgur.com/hqr00ez.png)
![overview - project background 2](https://i.imgur.com/fvTblq8.png)
![overview - project background 3](https://i.imgur.com/vxrMvtE.png)
<br>

### P;T 기능

![overview - project services](https://i.imgur.com/AdgxCNH.png)

<br />
<br />

## 3. 기술 스택

![technical stack - frontend and backend](https://i.imgur.com/iCQCBGR.png)
![technical stack - infra and communication](https://i.imgur.com/oX0GKJn.png)

<br />
<br />

## 4. 형상 관리

### Notion

![managiment tool - notion](https://i.imgur.com/ycjWigm.png)

### Jira

![managiment tool - jira](https://i.imgur.com/pJ9O31b.png)

<br />
<br />

## 5. 주기능 소개

### 사용자별 권한 및 역할

* 관리자 : 클래스 개설 승인/거절 권한
* 트레이너 : 클래스 개설 신청 권한, 트레이닝룸 개설 권한
* 수강생 : 클래스 수강 신청 및 찜하기 권한, 본인 수업 녹화본 시청 권한
* 공통 권한 : 로그인, 로그아웃, 회원가입, 마이페이지 조회/수정, 클래스 상세 조회, 상세 검색
* 비회원 이용 가능 서비스 : 회원가입, 클래스 목록 조회, 클래스 상세 조회, 상세 검색

<br />

### 주기능 1) 클래스 개설

![page - class opening](https://i.imgur.com/gCjZZ9r.png)

>**트레이너**가 클래스 개설 신청을 하게됩니다. **관리자**는 관리자 클래스 목록조회에서 클래스 개설 검수를 하여 승인/거절을 합니다.

ㅤ

### 주기능 2) 클래스 신청

![page - class enrollment](https://i.imgur.com/kH53ltP.png)

>**관리자**로부터 승인 받은 클래스들만 **수강생**들에게 보여지며, **수강생**들은 이 클래스들의 상세정보를 조회할 수 있습니다. 상세보기 화면에서 클래스를 보고 원하는 클래스를 찜목록에 두거나, 신청을 할 수 있습니다.
>

<br />

### 주기능 3) PT Room 개설 및 입장

![page - pt room opening and entering 1](https://i.imgur.com/QrrXzys.png)
![page - pt room opening and entering 2](https://i.imgur.com/BmUsRT6.png)

>**트레이너**는 **관리자**로부터 본인이 개설한 클래스가 승인되면, 본인의 **마이클래스 – 수강중클래스** 탭에서 개설한 클래스 목록 조회가 가능하고 PT룸을 개설할 수 있게 됩니다.

>**수강생**은 본인이 신청한 클래스를 본인의 **마이클래스 – 수강중클래스** 탭에서 신청한 클래스 목록조회가 가능하고 **트레이너**가 방을 개설해야만 PT룸에 입장할 수 있게 됩니다.

<br />

### 주기능 4) 화상 수업 및 녹화

![page - pt room recording 1](https://i.imgur.com/E8ukPXQ.png)
![page - pt room recording 2](https://i.imgur.com/p4J9s5U.png)

>화상 PT룸에 들어오면 개인별 영상이 녹화가 시작되며, 종료하게 되면 그 영상은 저장이 됩니다. 사용자는 그 영상을 녹화된 영상 목록에서 조회해서 다시보기와 다운로드가 가능합니다.


<br />
<br />

## 6. 서버 구조

### WebRTC 구조

![architecture - webrtc](https://i.imgur.com/KMlJjiw.png)

> WebRTC 는 크게 통화기능, 녹화기능이 있습니다. 이를 구현하기 위해 OpenVidu로 클라이언트를 만들고, 서버로는 OpenVidu 서버와 Kurento 미디어 서버가 사용되고 있습니다. 이들은 각각 REST API, WebSocket, webRTC로 통신하고 있습니다.

* WebRTC : WebSocket을 사용해서 1:1 (Peer to Peer) 통화 API
* Kurento : 1:1 만 가능한 WebRTC의 한계를 극복해주는 미디어서버
* OpenVidu: Kurento로 쉽게 개발할 수 있게 해주는 프레임워크

<br />

### REST API 통신 구조

![architecture - entire service](https://i.imgur.com/W9ICJrI.png)

> 사용자들이 로그인하여 클래스를 조회하는 웹페이지는 백엔드, 프론트엔드 서버에서 구성이 되고 화상 PT룸으로 입장할때는 443포트로 OpenVidu 서버에서 화상피티가 진행되고 녹화 비디오가 저장될때, 피티룸의 인원체크를 할때 백엔드서버와도 통신을 합니다.

<br />
<br />

## 7. 데이터베이스 구조

![db erd](https://i.imgur.com/Jy448A1.png)

<br />
<br />

## 8. 실행 방법

### BackEnd

1.  디렉토리를 이동합니다.

```
cd backend
```

2. gradle 프로젝트를 빌드합니다.

```
./gradlew build
```

3. `./gradlew build` 가 안되면 아래 명령어를 실행 후 빌드합니다.

```
chmod 777 gradlew
```

4. build/libs로 이동하고 생성된 .jar 파일을 실행합니다.

```
cd build/libs
java -jar backend-0.0.1-SNAPSHOT.jar
```

<br>

### FrontEnd

1. 디렉토리를 이동합니다.

```
cd frontend
```

2. 필요한 package들을 설치합니다.

```
npm install
```

3. 실행합니다.

```
npm run serve
```

<br>

### WebRTC

1. 아래 링크 참고하여 OpenVidu 서버를 설치하고 실행합니다. (단, Linux 환경에서만 설치 가능)
   https://docs.openvidu.io/en/2.19.0/deployment/ce/on-premises/

```
./openvidu start
```

2. 본 레포지토리에 포함된 OpenVidu 클라이언트를 실행합니다.

```
cd webRTC/pit_ptroom/mvn package exec:java
```

<br />
<br />

## 9. Contributor

- AhnDaeHyun : daehyun1023@naver.com
- KwakHoGeun : rootkwak528@gmail.com
- SeoMinYeong : 210113yynim@naver.com
- SeoYoSeph : tjdytpq0310@gmail.com
- YunYeongEun : ounog@naver.com

<br />
<br />

**Fin.**
