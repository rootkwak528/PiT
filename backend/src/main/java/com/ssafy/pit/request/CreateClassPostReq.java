package com.ssafy.pit.request;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClassPostReq {
	
	String classType;
	String classTitle;
	String classDesc;
	String classDay;
	String startDate;
	String endDate;
	String classStartTime;
	String classEndTime;
	String classCurri;
	String classMaterial;
	int classPrice;
	String classLevel;
	int classLimit;
	int classTcnt;
	
}
