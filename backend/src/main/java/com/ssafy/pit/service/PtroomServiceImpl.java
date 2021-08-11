package com.ssafy.pit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Ptroom;
import com.ssafy.pit.repository.ClassRepository;
import com.ssafy.pit.repository.PtroomRepository;

@Service("ptroomService")
public class PtroomServiceImpl implements PtroomService {

	@Autowired
	PtroomRepository ptroomRepository;
	
	@Autowired
	ClassRepository classRepository;
	
	@Override
	public void createPtroom(String ptroomUrl, int classNo) {
		Ptroom ptroom = new Ptroom();
		Classes classes = classRepository.findClassByClassNo(classNo);
		ptroom.setClasses(classes);
		ptroom.setPtroomLimit(classes.getClassLimit());
		ptroom.setPtroomUrl(ptroomUrl);
		ptroomRepository.save(ptroom);
		return;
	}
	
}
