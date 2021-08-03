package com.ssafy.pit.entity;

import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="event")
@Getter
@Setter
public class Event {
	
	@Id
	@Column(name="event_no")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int eventNo;
	@Column(name="event_title")
	String eventTitle;
	@Column(name="event_content")
	String eventContent;
//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Asia/Seoul")
	@Column(name="event_start_time")
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date eventStartTime;
	
	@Column(name="event_end_time")
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date eventEndTime;
	
	@Column(name="event_image")
	String eventImage;
}
