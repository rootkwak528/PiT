// API
import $axios from 'axios'

// 회원가입
export function register({ state }, payload) {
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

// 이메일 중복 체크
export function checkDuplicatedEmail({ state }, payload) {
  const url = `/users/email/${payload.userEmail}`
  return $axios.get(url)
}

// 닉네임 중복 체크
export function checkDuplicatedNickname({ state }, payload) {
  const url = `/users/nickname/${payload.userNickname}`
  return $axios.get(url)
}

// 로그인
export function requestLogin({ state }, payload) {
  const url = `/auth/login`
  let body = payload
  return $axios.post(url, body)
}

// 회원 정보 조회
export function requestUserInfo() {
  // console.log('requestUserInfo token : ' + token)
  const url = `/users/me`
  let token = "Bearer " + localStorage.getItem('jwt-auth-token')
  return $axios.get(url, {
    headers: {
      'Authorization' : token
    }
  })
}

// 회원 정보 수정
export function updateUserInfo({ state }, payload) {
  const url = `/users/me`
  let body = payload

  console.log(body.token)
  // let token = "Bearer " + localStorage.getItem('jwt-auth-token')
  return $axios.post(url, body, {
    headers: {
      'Authorization': body.token,
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 클래스 전체 목록 조회
export function getClassList({ state }) {
  const url = `/class`
  return $axios.get(url)
}
