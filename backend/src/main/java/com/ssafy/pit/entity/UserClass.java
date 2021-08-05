package com.ssafy.pit.entity;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_class")
@Getter
@Setter
public class UserClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_class_no")
	int userClassNo;
	
	@JsonManagedReference
	@OneToMany(mappedBy="userClass")
	List<Comment> Comments = new ArrayList<Comment>();
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_no")
	User user;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="class_no")
	Classes classes;
	
}
