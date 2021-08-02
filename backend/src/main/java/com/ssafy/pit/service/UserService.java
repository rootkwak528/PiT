package com.ssafy.pit.service;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.UserRegisterPostReq;

public interface UserService {
	User registerUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserEmail(String userEmail);
	User getUserByUserNickname(String userNickname);
}
