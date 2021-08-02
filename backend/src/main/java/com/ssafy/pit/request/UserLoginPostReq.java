package com.ssafy.pit.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginPostReq {
	
	String userEmail;
	String userPwd;
}
