package com.ssafy.pit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Ptroom;
import com.ssafy.pit.repository.ClassRepository;
import com.ssafy.pit.repository.PtroomRepository;
import com.ssafy.pit.repository.PtroomRepositorySupport;

@Service("ptroomService")
public class PtroomServiceImpl implements PtroomService {

	@Autowired
	PtroomRepository ptroomRepository;
	
	@Autowired
	PtroomRepositorySupport ptroomRepositorySupport;
	
	@Autowired
	ClassRepository classRepository;
	
	@Override
	public void createPtroom(String ptroomSessionName, int classNo) {
		Ptroom ptroom = new Ptroom();
		Classes classes = classRepository.findClassByClassNo(classNo);
		ptroom.setClasses(classes);
		ptroom.setPtroomLimit(classes.getClassLimit());
		ptroom.setPtroomSessionName(ptroomSessionName);
		ptroomRepository.save(ptroom);
		return;
	}

	@Override
	public int enterPtroom(int classNo) {
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		int updateUcnt = ptroom.getPtroomUcnt() + 1;
		ptroom.setPtroomUcnt(updateUcnt);
		ptroomRepository.save(ptroom);
		return updateUcnt;
	}

	@Override
	public void leavePtroom(int classNo) throws Exception {
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		ptroom.setPtroomUcnt(0);
		ptroomRepository.save(ptroom);
		return;
	}

	@Override
	public int getUserCnt(int classNo) throws Exception {
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		int userCnt = ptroom.getPtroomUcnt();
		return userCnt;
	}
	
	
}
