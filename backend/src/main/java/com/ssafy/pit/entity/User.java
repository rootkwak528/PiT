package com.ssafy.pit.entity;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_no")
	int userNo;
	
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	List<UserClass> userClasses = new ArrayList<UserClass>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	List<PtroomUser> ptroomUsers = new ArrayList<PtroomUser>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	List<UserLikes> userLikes = new ArrayList<UserLikes>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	List<Classes> classesList = new ArrayList<Classes>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="user")
	List<UserVideo> userVideos = new ArrayList<UserVideo>();
	
	@Column(name="user_gender")
	String userGender;
	@Column(name="user_name")
	String userName;
	@Column(name="user_email")
	String userEmail;
	@Column(name="user_type")
	String userType;
	@Column(name="user_profile")
	String userProfile;
	@Column(name="user_nickname")
	String userNickname;
	@Column(name="user_desc")
	String userDesc;
	@Column(name="user_phone")
	String userPhone;
	
	@JsonIgnore
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	String userPwd;
	
}