package com.ssafy.pit.response;

import java.util.Date;
import java.util.List;

import com.ssafy.pit.entity.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassDetailGetRes {
	
	List<String> photoUrls;
	String classTitle;
	String classDesc;
	String classCurri;
	List<CommentRes> comments;
	Date classStartDate;
	Date classEndDate;
	String classStartTime;
	String classEndTime;
	String classTypeName;
	String classLevelName;
	String classMaterial;
	int classTcnt;
	int classUcnt;
	int classLimit;
	int classPrice;
	String classTeacherName;
	String classDay;
	String classPermission;
	
}
