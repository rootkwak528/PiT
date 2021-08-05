package com.ssafy.pit.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.UserInfoPutReq;
import com.ssafy.pit.request.UserRegisterPostReq;
import com.ssafy.pit.response.UserInfoGetRes;

public interface UserService {
	User registerUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserEmail(String userEmail);
	User getUserByUserNickname(String userNickname);
	UserInfoGetRes getUserInfo(User user);
	int update(User user, UserInfoPutReq userUpdateInfo);
	int delete(String userEmail);
	int validateUserType(String userEmail);
}
