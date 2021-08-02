package com.ssafy.pit.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoPutReq {
	
	String userName;
	String userPwd;
	String userProfile;
	String userNickname;
	String userDesc;
	String userPhone;
	
}
