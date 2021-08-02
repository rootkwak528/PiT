package com.ssafy.pit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="feedback")
@Getter
@Setter
public class Feedback {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="feedback_no")
	int feedbackNo;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="video_no")
	UserVideo userVideo;
	
	@Column(name="feedback_answer")
	String feedbackAnswer;
	@Column(name="feedback_question")
	String feedbackQuestion;
	
}
