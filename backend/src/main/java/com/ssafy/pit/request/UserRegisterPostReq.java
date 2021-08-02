package com.ssafy.pit.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterPostReq {
	String userGender;
	String userName;
	String userEmail;
	String userType;
	String userProfile;
	String userNickname;
	String userDesc;
	String userPhone;
	String userPwd;
}
