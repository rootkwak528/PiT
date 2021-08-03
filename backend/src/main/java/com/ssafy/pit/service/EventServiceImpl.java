package com.ssafy.pit.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.entity.Event;
import com.ssafy.pit.repository.EventRepository;
import com.ssafy.pit.request.EventInfoReq;

@Service("eventService")
public class EventServiceImpl implements EventService {

	// 이미지 생성폴더 이름
	String uploadFolder = "upload";
	// 자기 이미지 생성할 경로
	String uploadPath = "C:" + File.separator + "Users" + File.separator + "ahnda" + File.separator
			+ "ssafy5-study" + File.separator + "Second" + File.separator + "Projects" + File.separator + "CommonProject" 
			+ File.separator + "S05P13A204" + File.separator + "backend" + File.separator + "src" + File.separator + "main"
			+ File.separator + "resources" + File.separator + "static";
	
	@Autowired
	EventRepository eventRepository;
	@Override
	public List<Event> getEventInfoList() {
		return eventRepository.findAll();
	}
	@Override
	public int registerEvent(EventInfoReq eventInfo, MultipartHttpServletRequest request) {
		
		try {
			Event event = new Event();
			
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if(!uploadDir.exists()) uploadDir.mkdir();
			
			MultipartFile part = request.getFiles("file").get(0);
			String fileName = part.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String extension = FilenameUtils.getExtension(fileName);
			String savingFileName = uuid + "." + extension;
			File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
			System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
			part.transferTo(destFile);
			String fileUrl = uploadFolder + "/" + savingFileName;
			
			event.setEventImage(fileUrl);
			event.setEventContent(eventInfo.getEventContent());
			event.setEventTitle(eventInfo.getEventTitle());
			event.setEventStartTime(eventInfo.getEventStartTime());
			event.setEventEndTime(eventInfo.getEventEndTime());
			
			eventRepository.save(event);
			return 1;
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
