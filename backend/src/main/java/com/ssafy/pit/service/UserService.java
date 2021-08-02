package com.ssafy.pit.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.UserRegisterPostReq;
import com.ssafy.pit.response.UserInfoGetRes;
import com.ssafy.pit.response.UserInfoPutReq;

public interface UserService {
	User registerUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserEmail(String userEmail);
	User getUserByUserNickname(String userNickname);
	UserInfoGetRes getUserInfo(String userEmail);
	int update(User user, UserInfoPutReq userUpdateInfo, MultipartHttpServletRequest request);
	int delete(String userEmail);
}
