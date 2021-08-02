package com.ssafy.pit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserLoginPostRes {

	String accessToken;
	String message;
	int statusCode;
	
	public UserLoginPostRes(int statusCode, String message, String accessToken) {
		this.accessToken = accessToken;
		this.message = message;
		this.statusCode = statusCode;
	}
	
	
}
