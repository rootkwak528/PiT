package com.ssafy.pit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.pit.common.auth.PitUserDetails;
import com.ssafy.pit.common.response.BaseResponseBody;
import com.ssafy.pit.service.ClassService;
import com.ssafy.pit.service.PtroomService;
import com.ssafy.pit.service.UserService;

@RequestMapping("/v1/ptroom/")
@Controller
public class PtroomController {

	@Autowired
	UserService userService;
	
	@Autowired
	ClassService classService;
	
	@Autowired
	PtroomService ptroomService;
	
	// PTroom 생성
	@PostMapping()
	public ResponseEntity<BaseResponseBody> createPtroom(Authentication authentication){
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userType = userDetails.getUser().getUserType();
		System.out.println(userType);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "ptroom이 개설되었습니다."));
	}
	
	
	// PTroom 입장
	@PutMapping("/enter/{classNo}")
	public ResponseEntity<Integer> enterPtroom(Authentication authentication, @PathVariable int classNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
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
	
	// PTroom 퇴장
	@PutMapping("/leave/{classNo}")
	public ResponseEntity<BaseResponseBody> leavePtroom(Authentication authentication, @PathVariable int classNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
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
