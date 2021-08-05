package com.ssafy.pit.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	public int registerEvent(EventInfoReq eventInfo) {
		
		try {
			Event event = new Event();
			
//			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
//			if(!uploadDir.exists()) uploadDir.mkdir();
//			MultipartFile part = request.getFiles("file").get(0);
//			String fileName = part.getOriginalFilename();
//			UUID uuid = UUID.randomUUID();
//			String extension = FilenameUtils.getExtension(fileName);
//			String savingFileName = uuid + "." + extension;
//			File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//			System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//			part.transferTo(destFile);
//			String fileUrl = uploadFolder + "/" + savingFileName;
			event.setEventImage(eventInfo.getEventImage());
			event.setEventContent(eventInfo.getEventContent());
			event.setEventTitle(eventInfo.getEventTitle());
			String startTime = eventInfo.getEventStartTime();
			String endTime = eventInfo.getEventEndTime();

			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date eventStartTime = fm.parse(startTime);
			Date eventEndTime = fm.parse(endTime);
			
			event.setEventStartTime(eventStartTime);
			event.setEventEndTime(eventEndTime);
			
			eventRepository.save(event);
			return 1;
			
			
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public int deleteEvent(int eventNo) {
		String deleteFileUrl = eventRepository.findUserByEventNo(eventNo).getEventImage();
		File file = null;
        if(deleteFileUrl != null) {
           file = new File(uploadPath + File.separator, deleteFileUrl);
           if(file.exists()) {
              file.delete();
           }
        }
		
		return eventRepository.deleteByEventNo(eventNo);
	}
	@Override
	public int updateEvent(int eventNo, EventInfoReq eventInfo) {
		
		try {
			Event event = eventRepository.findUserByEventNo(eventNo);
			
//			String deleteFileUrl = eventRepository.findUserByEventNo(eventNo).getEventImage();
//			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
//			if(!uploadDir.exists()) uploadDir.mkdir();
//			
//			
//			File file = null;
//	        if(deleteFileUrl != null) {
//	           file = new File(uploadPath + File.separator, deleteFileUrl);
//	           if(file.exists()) {
//	              file.delete();
//	           }
//	        }
			
//			MultipartFile part = request.getFiles("file").get(0);
//			String fileName = part.getOriginalFilename();
//			UUID uuid = UUID.randomUUID();
//			String extension = FilenameUtils.getExtension(fileName);
//			String savingFileName = uuid + "." + extension;
//			File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//			System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
//			part.transferTo(destFile);
//			String fileUrl = uploadFolder + "/" + savingFileName;
			
			event.setEventImage(eventInfo.getEventImage());
			event.setEventTitle(eventInfo.getEventTitle());
			event.setEventContent(eventInfo.getEventContent());

			String startTime = eventInfo.getEventStartTime();
			String endTime = eventInfo.getEventEndTime();
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date eventStartTime = fm.parse(startTime);
			Date eventEndTime = fm.parse(endTime);
			
			event.setEventStartTime(eventStartTime);
			event.setEventEndTime(eventEndTime);
			
			eventRepository.save(event);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
