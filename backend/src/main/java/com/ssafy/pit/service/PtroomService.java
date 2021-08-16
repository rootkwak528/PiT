package com.ssafy.pit.service;

public interface PtroomService {

	void createPtroom(String ptroomSessionName, int classNo);
	Integer enterPtroom(int classNo) throws Exception;
	void leavePtroom(int classNo) throws Exception;
	int getUserCnt(int classNo) throws Exception;
}
