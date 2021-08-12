// export function setPlatform(state, isDesktop) {
//   state.isDesktopPlatform = isDesktop;
// }

// export function setMenuActive(state, index) {
//   console.log("setMenuActive", state, index);
//   const keys = Object.keys(state.menus);
//   state.activeMenu = keys[index];
// }

// export function setMenuActiveMenuName(state, menuName) {
//   state.activeMenu = menuName;
// }

export function setIsLogined(state, isLogined) {
  state.isLogined = isLogined;
}

export function setProfileUrl(state, profileUrl) {
  state.profileUrl = profileUrl;
}

export function setUserType(state, userType) {
  state.userType = userType;
}

export function setUserNickname(state, userNickname) {
  state.userNickname = userNickname;
}
