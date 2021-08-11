package com.ssafy.pit.service;

public interface PtroomService {

	void createPtroom(String ptroomUrl, int classNo);
	int enterPtroom(int classNo) throws Exception;
	void leavePtroom(int classNo) throws Exception;
}
