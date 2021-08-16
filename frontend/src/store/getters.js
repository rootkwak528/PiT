
export function getIsLogined(state) {
  return state.isLogined;
}

export function getProfileUrl(state) {
  return state.profileUrl;
}

export function getUserType(state) {
  return state.userType;
}

export function getUserNickname(state) {
  return state.userNickname;
}

export function getUserName(state) {
  return state.userName;
}

export function getUserNo(state) {
  return state.userNo;
}

export function getSearchType(state) {
  return state.searchType;
}

export function getSearchKeyword(state) {
  return state.searchKeyword;
}

export function getClassType(state) {
  return state.classType;
}

export function getClassLevel(state) {
  return state.classLevel;
}

export function getClassDay(state) {
  return state.classDay;
}

export function getClassStartTime(state) {
  return state.classStartTime;
}

export function getClassEndTime(state) {
  return state.classEndTime;
}

export function getClassList(state) {
  return state.classList;
}

export function getUserLikesClassList(state) {
  return state.userLikesClassNo;
}

// // 플랫폼 관련 정보 가져오기
// export function getIsDesktopPlatform(state) {
//   return state.isDesktopPlatform;
// }
// // 메뉴 객체 가져오기
// export function getMenus(state) {
//   return state.menus;
// }
// // Active된 메뉴 인덱스 가져오기
// export function getActiveMenuIndex(state) {
//   const keys = Object.keys(state.menus);
//   return keys.findIndex(item => item === state.activeMenu);
// }
