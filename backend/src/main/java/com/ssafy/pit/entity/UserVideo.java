package com.ssafy.pit.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_video")
@Getter
@Setter
public class UserVideo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="video_no")
	int videoNo;
	
	@JsonManagedReference
	@OneToMany(mappedBy="userVideo")
	List<Feedback> feedbacks = new ArrayList<Feedback>();
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_no")
	User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="ptroom_no")
	Ptroom ptroom;
	
	@Column(name="video_savetime")
	@Temporal(TemporalType.TIMESTAMP)
	Date videoSavetime;
	@Column(name="video_url")
	String videoUrl;
	
}
