// API
import $axios from "axios";

// 회원가입
export function register({ state }, payload) {
  const url = "/users";
  let body = payload;
  return $axios.post(url, body);
}

// 이메일 중복 체크
export function checkDuplicatedEmail({ state }, payload) {
  const url = `/users/email/${payload.userEmail}`;
  return $axios.get(url);
}

// 닉네임 중복 체크
export function checkDuplicatedNickname({ state }, payload) {
  const url = `/users/nickname/${payload.userNickname}`;
  return $axios.get(url);
}

// 닉네임 중복 체크 (회원 정보 수정 시)
export function checkDuplicatedUpdateNickname({ state }, payload) {
  const url = `/users/me/nickname/${payload.userNickname}`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// 로그인
export function requestLogin({ state }, payload) {
  const url = `/auth/login`;
  let body = payload;
  return $axios.post(url, body);
}

// 회원 정보 조회
export function getUserInfo() {
  // console.log('requestUserInfo token : ' + token)
  const url = `/users/me`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// 회원 정보 수정
export function updateUserInfo({ state }, payload) {
  const url = `/users/me`;
  let body = payload;
  console.log(body.token);
  // let token = "Bearer " + localStorage.getItem('jwt-auth-token')
  return $axios.put(url, body, {
    headers: {
      Authorization: body.token
    }
  });
}

// 회원 탈퇴
export function deleteUser() {
  const url = `/users/me`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.delete(url, {
    headers: {
      Authorization: token
    }
  });
}

// 유저 프로필 변경
export function updateProfile({ state }, payload) {
  const url = `/users/me/profile`;
  let body = payload;
  return $axios.put(url, body, {
    headers: {
      Authorization: body.token
    }
  });
}

// 클래스 전체 목록 조회
export function getClassList({ state }) {
  const url = `/class`;
  return $axios.get(url);
}

// 클래스 검색하기
export function getSearchClassList({ state }, payload) {
  let p = payload;
  const url = `/class?searchType=${p.searchType}&searchKeyword=${p.searchKeyword}
    &classType=${p.classType}&classLevel=${p.classLevel}&classDay=${p.classDay}
    &classStartTime=${p.classStartTime}&classEndTime=${p.classEndTime}`;
  return $axios.get(url);
}

// 클래스 상세 조회
export function getClassDetail({}, payload) {
  let body = payload;
  const url = `/class/${body.classNo}`;
  return $axios.get(url);
}

// [관리자] 클래스 목록 조회
export function getAdminClassList({ state }, payload) {
  const url = `/class/admin?permission=${payload.permission}`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// [관리자] 클래스 상세 조회
export function getAdminClassDetail({ state }, payload) {
  const url = `/class/admin/${payload.classNo}`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// [관리자] 해당 클래스 승인 여부 상태 변경
export function updateClassPermission({ state }, payload) {
  const url = `/class/admin/${payload.classNo}`;
  let body = payload;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.put(url, body, {
    headers: {
      Authorization: token
    }
  });
}

// 수강중 클래스 목록 조회
export function getRegisterClassList() {
  const url = `/class/registerclass`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// 수강완료 클래스 목록 조회
export function getFinishedClassList() {
  const url = `/class/finishedclass`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// 클래스 생성 요청
export function createClass({ state }, payload) {
  const url = `/class/create`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  let body = payload;
  console.log("body : " + body);
  return $axios.post(url, body, {
    headers: {
      Authorization: token
    }
  });
}

// 찜한 클래스 목록 조회 -  /class/likes  GET
export function getClassLikesList() {
  const url = `/class/likes`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  });
}

// 찜한 클래스 등록 -  /class/likes/{classNo}  POST
export function registerClassLikes({ state }, payload) {
  const url = `/class/likes/${payload.classNo}`;
  let body = payload;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.post(url, body, {
    headers: {
      Authorization: token
    }
  });
}

// 찜한 클래스 삭제 -  /class/likes/{classNo}  DELETE
export function deleteClassLikes({ state }, payload) {
  const url = `/class/likes/${payload.classNo}`;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.delete(url, {
    headers: {
      Authorization: token
    }
  });
}

// 클래스 신청하기
export function enrollClass({ state }, payload) {
  const url = `/class/enrollment/${payload.classNo}`;
  let body = payload;
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.put(url, body, {
    headers: {
      Authorization: token
    }
  });
}

// 세션 네임 조회 - /ptroom/enter/{classNo}  GET
export function getSessionName({ state }, payload) {
  const url = `/ptroom/enter/${payload.classNo}`
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  })
}

// 세션 개설여부 조회 - /ptroom/{classNo}  GET
export function getSessionAvail({ state }, payload) {
  const url = `/ptroom/${payload.classNo}`
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.get(url, {
    headers: {
      Authorization: token
    }
  })
}

// 세션 입장 - /ptroom/enter/{classNo}  PUT
export function enterSession({ state }, payload) {
  const url = `/ptroom/enter/${payload.classNo}`
  let token = "Bearer " + localStorage.getItem("jwt-auth-token");
  return $axios.put(url, {}, {
    headers: {
      Authorization: token
    }
  })
}

// 이벤트 목록 조회
export function getEventInfoList({ state }) {
  const url = `/event`
  return $axios.get(url);
}

// 이벤트 상세 조회
export function getEventDetail({ state }, payload) {
  const url = `/event/detail/${payload.eventNo}`
  return $axios.get(url);
}
