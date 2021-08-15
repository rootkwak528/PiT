package com.ssafy.pit.controller;

import java.util.List;

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

import com.ssafy.pit.common.auth.PitUserDetails;
import com.ssafy.pit.common.response.BaseResponseBody;
import com.ssafy.pit.entity.Event;
import com.ssafy.pit.request.EventInfoReq;
import com.ssafy.pit.service.EventService;
import com.ssafy.pit.service.UserService;


@RestController
@RequestMapping("/v1/event")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("")
	public ResponseEntity<List<Event>> getEventInfoList() {
		List<Event> eventInfoList = eventService.getEventInfoList();
		for (Event e: eventInfoList) {
			System.out.println(e.getEventStartTime());
		}
		
		return ResponseEntity.status(200).body(eventInfoList);
	}
	
	@GetMapping("/{eventNo}")
	public ResponseEntity<Event> getEventDetail(@PathVariable int eventNo) {
		Event event = eventService.getEventDetail(eventNo);
		return ResponseEntity.status(200).body(event);
	}
	
	@PostMapping("")
	public ResponseEntity<BaseResponseBody> registerEvent(Authentication authentication, 
			@RequestBody EventInfoReq eventInfo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		if(userService.validateUserType(userEmail) == 1) {
			if(eventService.registerEvent(eventInfo) == 1) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "이벤트 등록에 성공하였습니다."));				
			}
			else {				
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "이벤트 등록과정에 문제가 발생하였습니다."));
			}
		}
		else {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "접근할 수 없는 페이지입니다."));
		}

	}
	
	@DeleteMapping("/{eventNo}")
	public ResponseEntity<BaseResponseBody> deleteEvent(Authentication authentication, 
			@PathVariable int eventNo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		
		if(userService.validateUserType(userEmail) == 1) {
			if (eventService.deleteEvent(eventNo) == 1) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "해당 이벤트를 삭제하였습니다."));
			}
			else {
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "이벤트 삭제과정에 문제가 발생하였습니다."));
			}
		}
		else {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "접근할 수 없는 페이지입니다."));
		}

	}
	
	@PutMapping("/{eventNo}")
	public ResponseEntity<BaseResponseBody> updateEvent(Authentication authentication, 
			@PathVariable int eventNo, @RequestBody EventInfoReq eventInfo) {
		PitUserDetails userDetails = (PitUserDetails) authentication.getDetails();
		String userEmail = userDetails.getUsername();
		
		if(userService.validateUserType(userEmail) == 1) {
			if(eventService.updateEvent(eventNo, eventInfo) == 1) {
				return ResponseEntity.status(200).body(BaseResponseBody.of(200, "해당 이벤트 정보를 수정하였습니다."));				
			}
			else {
				return ResponseEntity.status(404).body(BaseResponseBody.of(404, "이벤트를 수정하는 과정에 문제가 발생하였습니다."));
			}
		}
		else {
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "접근할 수 없는 페이지입니다."));
		}
	}
}
