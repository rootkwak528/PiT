// API
import $axios from 'axios'

export function register({ state }, payload) {
  const url = '/users'
  let body = payload
  return $axios.post(url, body)
}

export function checkDuplicatedEmail({ state }, payload) {
  const url = `/users/email/${payload.userEmail}`
  return $axios.get(url)
}

export function checkDuplicatedNickname({ state }, payload) {
  const url = `/users/nickname/${payload.userNickname}`
  return $axios.get(url)
}
// skeleton 예시 참고
// export function requestLogin ({ state }, payload) {
//   console.log('requestLogin', state, payload)
//   const url = '/auth/login'
//   let body = payload
//   return $axios.post(url, body)
// }

// export function requestUserInfo ({}, token){
//   console.log('requestUserInfo token : ' + token)
//   const url = '/api/v1/users/me'
//   return $axios.get(url, {
//     headers: {
//       'Authorization' : token
//     }
//   })
// }

// export function requestJoin ({ state }, payload) {
//   const url = '/users'
//   let body = payload
//   return $axios.post(url, body)
// }

// export function checkDuplicationId ({ state }, payload) {
//   const url = `/users/${payload.userId}`
//   return $axios.get(url)
// }
// skeleton 예시 참고

