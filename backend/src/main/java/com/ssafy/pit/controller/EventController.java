package com.ssafy.pit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.pit.entity.Event;
import com.ssafy.pit.service.EventService;


@RestController
@RequestMapping("/v1/event")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@GetMapping("")
	public ResponseEntity<List<Event>> getEventInfoList() {
		List<Event> eventInfoList = eventService.getEventInfoList();
		for (Event e: eventInfoList) {
			System.out.println(e.getEventStartTime());
		}
		
		return ResponseEntity.status(200).body(eventInfoList);
	}
	
//	@GetMapping("/{eventNo}")
//	public ResponseEntity<EventInfoGetRes> getEventInfo(@PathVariable int eventNo){
//		return null;
//	}
	
}
