package com.ssafy.pit.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassSearchGetReq {
	
	String searchType;
	String searchKeyword;
	String classType;
	String classLevel;
	String classDay;
	String classStartTime;
	String classEndTime;
	
	@Override
	public String toString() {
		return "ClassSearchGetReq [searchType=" + searchType + ", searchKeyword=" + searchKeyword + ", classType="
				+ classType + ", classLevel=" + classLevel + ", classDay=" + classDay + ", classStartTime="
				+ classStartTime + ", classEndTime=" + classEndTime + "]";
	}
	
	
	
}
