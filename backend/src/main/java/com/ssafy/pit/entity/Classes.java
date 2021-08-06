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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="classes")
public class Classes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="class_no")
	int classNo;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="user_no")
	User user;
	
	@JsonManagedReference
	@OneToOne(mappedBy="classes")
	Ptroom ptroom;
	
	@JsonManagedReference
	@OneToMany(mappedBy="classes")
	List<UserClass> userClasses = new ArrayList<UserClass>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="classes")
	List<UserLikes> userLikes = new ArrayList<UserLikes>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="classes")
	List<ClassPhoto> classPhotos = new ArrayList<ClassPhoto>();
	
	@Column(name="class_type")
	String classType;
	
	@Column(name="class_title")
	String classTitle;
	
	@Column(name="class_desc")
	String classDesc;
	
	@Column(name="class_day")
	String classDay;
	
	@Column(name="class_start_date")
	@Temporal(TemporalType.DATE)
	Date classStartDate;
	
	@Column(name="class_end_date")
	@Temporal(TemporalType.DATE)
	Date classEndDate;
	
	@Column(name="class_start_time")
	String classStartTime;
	
	@Column(name="class_end_time")
	String classEndTime;
	
	@Column(name="class_curri")
	String classCurri;
	
	@Column(name="class_material")
	String classMaterial;
	
	@Column(name="class_price")
	int classPrice;
	
	@Column(name="class_level")
	String classLevel;
	
	@Column(name="class_limit")
	int classLimit;
	
	@Column(name="class_ucnt")
	int classUcnt;
	
	@Column(name="class_tcnt")
	int classTcnt;
	
	@Column(name="class_ccnt")
	int classCcnt;
	
	@Column(name="class_teacher_name")
	String classTeacherName;
	
	@Column(name="class_permission", columnDefinition = "char(5) default '002'")
	String classPermission;
	
	
}
