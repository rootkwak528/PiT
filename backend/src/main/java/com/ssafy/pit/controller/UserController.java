package com.ssafy.pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.common.auth.PitUserDetails;
import com.ssafy.pit.common.response.BaseResponseBody;
import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.UserRegisterPostReq;
import com.ssafy.pit.response.UserInfoGetRes;
import com.ssafy.pit.response.UserInfoPutReq;
import com.ssafy.pit.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	// Test Controller
	@GetMapping("/hello")
	public ResponseEntity<String> hello(){
		return ResponseEntity.ok("hello");
	}
	
	// 유저 등록
	@PostMapping()
	public ResponseEntity<? extends BaseResponseBody> register(@RequestBody UserRegisterPostReq registerInfo){
		User userGetByEmail = userService.getUserByUserEmail(registerInfo.getUserEmail());
		User userGetByNickname = userService.getUserByUserNickname(registerInfo.getUserNickname());
		if(userGetByEmail != null && userGetByNickname != null) {
			return ResponseEntity.status(411).body(BaseResponseBody.of(411, "Email과 닉네임 모두 사용중입니다."));
		}
		else if (userGetByEmail != null) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 가입된 Email입니다."));
		}
		else if (userGetByNickname != null) {
			return ResponseEntity.status(410).body(BaseResponseBody.of(410, "다른 회원이 사용하고 계신 닉네임입니다."));
		}
		else {
			User user = userService.registerUser(registerInfo);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "환영합니다. 회원가입에 성공하셨습니다."));
		}
//		User user = userService.registerUser(registerInfo);
//		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "환영합니다. 회원가입에 성공하셨습니다."));
	}
	
	// 이메일 중복확인
	@GetMapping("/email/{userEmail}")
	public ResponseEntity<? extends BaseResponseBody> checkDuplicatedEmail(@RequestBody @PathVariable String userEmail){
		User user = userService.getUserByUserEmail(userEmail);
		if (user != null) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 가입된 Email입니다."));
		}
		else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용할 수 있는 Email입니다."));
		}
	}
	
	// 닉네임 중복확인
	@GetMapping("/nickname/{userNickname}")
	public ResponseEntity<? extends BaseResponseBody> checkDuplicatedNickname(@RequestBody @PathVariable String userNickname){
		User user = userService.getUserByUserNickname(userNickname);
		if (user != null) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "중복된 닉네임입니다."));
		}
		else {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "사용할 수 있는 닉네입니다."));
		}
	}
	
	// 개인정보 조회
	@GetMapping("/me")
	public ResponseEntity<UserInfoGetRes> getUserInfo(Authentication authentication) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		UserInfoGetRes userInfo = userService.getUserInfo(userEmail);
		try {
			return ResponseEntity.status(200).body(userInfo);
		}
		catch(Exception e) {
			return ResponseEntity.status(404).body(null);
		}
	}
	
	// 개인정보 수정
	@PutMapping("/me")
	public ResponseEntity<String> updateUser(Authentication authentication, @RequestBody UserInfoPutReq userUpdateInfo,MultipartHttpServletRequest request) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		
		User user = userService.getUserByUserEmail(userEmail);
		
		if(userService.update(user, userUpdateInfo, request) == 1) {
			return ResponseEntity.status(200).body("회원정보 업데이트가 완료되었습니다.");
		}	
		else {
			return ResponseEntity.status(404).body("업데이트 과정에서 문제가 발생했습니다.");
		}
	}
	
	@DeleteMapping("/me")
	public ResponseEntity<String> deleteUser(Authentication authentication) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		
		if(userService.delete(userEmail) == 1) {
			return ResponseEntity.status(200).body("회원탈퇴에 성공하였습니다.");
		}	
		else {
			return ResponseEntity.status(404).body("회원탈퇴중에 문제가 발생하였습니다.");
		}
	}
}
