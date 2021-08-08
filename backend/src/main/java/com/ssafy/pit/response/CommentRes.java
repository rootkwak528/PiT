package com.ssafy.pit.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRes {
	
	String commentContent;
	Date commentWriteDate;
	String userNickname;
	
}
