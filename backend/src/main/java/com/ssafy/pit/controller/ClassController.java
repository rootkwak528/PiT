package com.ssafy.pit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;
import com.ssafy.pit.service.ClassService;

@RequestMapping("/v1/class")
@RestController
public class ClassController {
	
	@Autowired
	ClassService classService;
	
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
	
}
