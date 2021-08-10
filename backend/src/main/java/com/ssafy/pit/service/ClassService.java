package com.ssafy.pit.service;

import java.util.List;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.request.CreateClassPostReq;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;
import com.ssafy.pit.response.RegisterClassGetRes;

public interface ClassService {

	List<ClassListGetRes> getClassList(ClassSearchGetReq searchInfo);
	List<ClassListGetRes> getClassList(String classPermission);
	ClassDetailGetRes getClassDetail(int classNo, String classPermission);
	List<ClassListGetRes> getClassLikesList(int userNo);
	int registerClassLikes(int userNo, int classNo);
	int deleteClassLikes(int userNo, int classNo);
	List<ClassListGetRes> getFinishedClassList(int userNo);
	List<RegisterClassGetRes> getRegisterClassList(int userNo);
	void createClass(CreateClassPostReq createClassInfo, User user) throws Exception;
	int getLatestClassNo() throws Exception;
	void createClassPhoto(String photo, int classNo, boolean isThumbnail) throws Exception;
	int enrollClass(User user, int classNo) throws Exception;
	void updateClassPermission(int classNo, String permission) throws Exception;
	
}
