package com.ssafy.pit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pit.common.auth.PitUserDetails;
import com.ssafy.pit.common.response.BaseResponseBody;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;
import com.ssafy.pit.response.RegisterClassGetRes;
import com.ssafy.pit.service.ClassService;
import com.ssafy.pit.service.UserService;

@RequestMapping("/v1/class")
@RestController
public class ClassController {
	
	@Autowired
	ClassService classService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/{classNo}")
	public ResponseEntity<ClassDetailGetRes> getClassDetail(@PathVariable int classNo){
		ClassDetailGetRes classDetail = classService.getClassDetail(classNo);
		return ResponseEntity.status(200).body(classDetail);
	}
	
	
	@GetMapping()
	public ResponseEntity<List<ClassListGetRes>> getClassList(ClassSearchGetReq searchInfo) {
		List<ClassListGetRes> classList = null;
		System.out.println(searchInfo.toString());
		
		if(searchInfo.getClassDay() == null && searchInfo.getClassEndTime() == null && searchInfo.getClassLevel() == null
				&& searchInfo.getClassType() == null && searchInfo.getClassStartTime() == null 
				&& searchInfo.getClassEndTime() == null && searchInfo.getSearchKeyword() == null) {
			System.out.println("홈 전체 클래스 리스트 조회");
			classList = classService.getClassList();		
		}
		else {
			classList = classService.getClassList(searchInfo);
		}
		return ResponseEntity.status(200).body(classList);
	}

	@GetMapping("/likes")
	public ResponseEntity<List<ClassListGetRes>> getClassLikesList(Authentication authentication) {
		List<ClassListGetRes> classLikesList = null;
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int userNo = userDetails.getUser().getUserNo();
		if(userService.validateUserType(userEmail) == 3) {
			classLikesList = classService.getClassLikesList(userNo);
			return ResponseEntity.status(200).body(classLikesList);
		}
		else {
			return ResponseEntity.status(404).body(classLikesList);
		}
	}
	
	@PostMapping("/likes/{classNo}")
	public ResponseEntity<BaseResponseBody> registerClassLikes(Authentication authentication, @PathVariable int classNo){
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int userNo = userDetails.getUser().getUserNo();
		if(userService.validateUserType(userEmail) == 3) {
			if(classService.registerClassLikes(userNo, classNo) == 1) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "찜한클래스로 등록되었습니다."));
			}
			else {				
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "등록과정에 문제가 생겼습니다."));
			}
		}
		else {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "접근할 수 없는 페이지입니다."));
		}
	}
	
	@DeleteMapping("/likes/{classNo}")
	public ResponseEntity<BaseResponseBody> deleteClassLikes(Authentication authentication, @PathVariable int classNo){
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int userNo = userDetails.getUser().getUserNo();
		if(userService.validateUserType(userEmail) == 3) {
			if(classService.deleteClassLikes(userNo, classNo) == 1) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "찜한클래스가 해제되었습니다."));
			}
			else {				
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "찜한 클래스 삭제과정에 문제가 생겼습니다."));
			}
		}
		else {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "접근할 수 없는 페이지입니다."));
		}
	}
	
	@GetMapping("/finishedclass")
	public ResponseEntity<List<ClassListGetRes>> getFinishedClassList(Authentication authentication) {
		List<ClassListGetRes> finishedClassList = null;
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int userNo = userDetails.getUser().getUserNo();
		if(userService.validateUserType(userEmail) == 3 || userService.validateUserType(userEmail) == 2) {
			finishedClassList = classService.getFinishedClassList(userNo);
			return ResponseEntity.status(200).body(finishedClassList);
		}
		else {
			return ResponseEntity.status(404).body(finishedClassList);
		}
	}
	
	@GetMapping("/registerclass")
	public ResponseEntity<List<RegisterClassGetRes>> getRegisterClassList(Authentication authentication) {
		List<RegisterClassGetRes> registerClassList = null;
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		int userNo = userDetails.getUser().getUserNo();
		if(userService.validateUserType(userEmail) == 3 || userService.validateUserType(userEmail) == 2) {
			registerClassList = classService.getRegisterClassList(userNo);
			return ResponseEntity.status(200).body(registerClassList);
		}
		else {
			return ResponseEntity.status(404).body(registerClassList);
		}
	}
	
	
	
	
	
}
