package com.ssafy.pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pit.common.util.JwtTokenUtil;
import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.UserLoginPostReq;
import com.ssafy.pit.response.UserLoginPostRes;
import com.ssafy.pit.service.UserService;

@RequestMapping("/v1/auth")
@RestController
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginPostRes> login(@RequestBody UserLoginPostReq loginInfo) {
		String userEmail = loginInfo.getUserEmail();
		String userPwd = loginInfo.getUserPwd();
		UserLoginPostRes userLoginPostRes = new UserLoginPostRes();
		
		
		try {
			User user = userService.getUserByUserEmail(userEmail);
			
//			if(passwordEncoder.matches(userPwd, user.getUserPwd())) {
//				return ResponseEntity.status(200).body(new UserLoginPostRes(200, "로그인에 성공하였습니다.", JwtTokenUtil.getToken(userEmail)));
//			}
			if( userPwd.equals(user.getUserPwd())) {
				return ResponseEntity.status(200).body(new UserLoginPostRes(200, "로그인에 성공하였습니다.", JwtTokenUtil.getToken(userEmail)));
			}
			else {
				// 유효하지 않은 패스워드인 경우, 로그인 실패로 응답
				return ResponseEntity.status(401).body(new UserLoginPostRes(401, "잘못된 비밀번호입니다.", null));
			}
		} catch(NullPointerException e) {
			return ResponseEntity.status(404).body(new UserLoginPostRes(404, "존재하지 않는 계정입니다.", null));
		}
	}
	
}
