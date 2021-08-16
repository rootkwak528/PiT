package com.ssafy.pit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.entity.Event;
import com.ssafy.pit.request.EventInfoReq;

public interface EventService {
	
	List<Event> getEventInfoList();
	int registerEvent(EventInfoReq eventInfo);
	int deleteEvent(int eventNo);
	int updateEvent(int eventNo, EventInfoReq eventInfo);
	Event getEventDetail(int eventNo);
}
