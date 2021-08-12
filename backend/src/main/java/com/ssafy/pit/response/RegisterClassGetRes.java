package com.ssafy.pit.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterClassGetRes {
	
	int classNo;
	int classPrice;
	String classThumbnail;
	String classTitle;
	String classTeacherName;
	String classDesc;
	float classPercentage;
	Date classStartDate;
	Date classEndDate;
	String classStartTime;
	String classEndTime;
	String ptroomUrl;
	String classType;
	
}
