package com.ssafy.pit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.Event;
import com.ssafy.pit.repository.EventRepository;

@Service("eventService")
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepository eventRepository;
	
	@Override
	public List<Event> getEventInfoList() {
		return eventRepository.findAll();
	}

}
