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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="ptroom")
@Getter
@Setter
public class Ptroom {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ptroom_no")
	int ptroomNo;
	
	@JsonManagedReference
	@OneToMany(mappedBy="ptroom")
	List<PtroomUser> ptroomUsers = new ArrayList<PtroomUser>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="ptroom")
	List<UserVideo> userVideos = new ArrayList<UserVideo>();
	
	@JsonBackReference
	@OneToOne
	@JoinColumn(name="class_no")
	Classes classes;
	
//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ptroom_savetime")
	String ptroomSavetime;
	@Column(name="ptroom_limit")
	int ptroomLimit;
	@Column(name="ptroom_ucnt")
	int ptroomUcnt;
	@Column(name="ptroom_url")
	String ptroomUrl;
	
}
