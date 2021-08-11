package com.ssafy.pit.response;

import java.time.LocalDate;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassListGetRes {
	
	int classNo;
	String classThumbnail;
	String classTitle;
	int classPrice;
	String classTeacherName;
	Date classStartDate;
	Date classEndDate;
	String videoUrl;
	
}
