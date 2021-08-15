package com.ssafy.pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.pit.common.auth.PitUserDetails;
import com.ssafy.pit.common.response.BaseResponseBody;
import com.ssafy.pit.repository.PtroomRepositorySupport;
import com.ssafy.pit.service.ClassService;
import com.ssafy.pit.service.PtroomService;
import com.ssafy.pit.service.UserService;

@CrossOrigin(
        origins = "https://i5a204.p.ssafy.io:5000/", 
        allowCredentials = "true", 
        allowedHeaders = "*", 
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT,RequestMethod.OPTIONS}
)
@RequestMapping("/v1/ptroom/")
@Controller
public class PtroomController {

	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	PtroomService ptroomService;
	
	@Autowired
	PtroomRepositorySupport ptroomRepositorySupport;
	
	// PTroom 인원 조회
	@GetMapping("/{classNo}")
	public ResponseEntity<Integer> getUserCnt(Authentication authentication, @PathVariable int classNo){
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		System.out.println("userType : "+userService.validateUserType(userEmail));
		if(userService.validateUserType(userEmail) == 1) {
			return ResponseEntity.status(403).body(null);
		}
		else {
			try {
				int userCnt = ptroomService.getUserCnt(classNo);
				return ResponseEntity.status(200).body(userCnt);				
			}
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(null);
			}
		}
	}
	
	
	// PTroom 세션 네임 반환
	@GetMapping("/enter/{classNo}")
	public ResponseEntity<String> getSessionName(Authentication authentication, @PathVariable int classNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		if(userService.validateUserType(userEmail) == 1) {
			return ResponseEntity.status(403).body(null);
		}
		else {
			try {
				String sessionName = ptroomRepositorySupport.getPtroomByClassNo(classNo).getPtroomSessionName();
				return ResponseEntity.status(200).body(sessionName);
			}
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(null);
			}
		}
	}
	
	// PTroom 입장
	@PutMapping("/enter/{classNo}")
	public ResponseEntity<Integer> enterPtroom(Authentication authentication, @PathVariable int classNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		System.out.println("userType : "+userService.validateUserType(userEmail));
		if(userService.validateUserType(userEmail) == 1) {
			return ResponseEntity.status(403).body(null);
		}
		else {
			try {
				int userCnt = ptroomService.enterPtroom(classNo);
				return ResponseEntity.status(200).body(userCnt);				
			}
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(null);
			}
		}
	}
	
	// PTroom 종료하기
	@PutMapping("/leave/{classNo}")
	public ResponseEntity<BaseResponseBody> leavePtroom(Authentication authentication, @PathVariable int classNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		System.out.println("userType : "+userService.validateUserType(userEmail));
		if(userService.validateUserType(userEmail) == 2) {
			try {
				ptroomService.leavePtroom(classNo);
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "ptroom이 종료되었습니다."));
			}
			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(500).body(BaseResponseBody.of(500, "종료에 오류가 생겼습니다."));
			}
			
		} else {
			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "접근할 수 없는 페이지입니다."));
		}
	}
	
}
