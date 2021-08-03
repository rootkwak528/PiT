package com.ssafy.pit.request;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventInfoReq {
	
	String eventTitle;
	String eventContent;
	String eventStartTime;
	String eventEndTime;
	@Override
	public String toString() {
		return "EventInfoReq [eventTitle=" + eventTitle + ", eventContent=" + eventContent + ", eventStartTime="
				+ eventStartTime + ", eventEndTime=" + eventEndTime + "]";
	}
	
}
