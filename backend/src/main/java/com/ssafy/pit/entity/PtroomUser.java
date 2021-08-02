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
@Table(name="ptroom_user")
@Getter
@Setter
public class PtroomUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ptroom_user_no")
	int ptroomUserNo;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_no")
	User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="ptroom_no")
	Ptroom ptroom;
	
}
