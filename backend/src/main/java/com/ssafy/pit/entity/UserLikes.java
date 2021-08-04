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
@Table(name="user_likes")
@Getter
@Setter
public class UserLikes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_likes_no")
	int userLikesNo;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_no")
	User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="class_no")
	Classes classes;
	
}
