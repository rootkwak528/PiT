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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="class_photo")
@Getter
@Setter
public class ClassPhoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="photo_no")
	int PhotoNo;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="class_no")
	Classes classes;
	
	@Column(name="photo_isthumbnail")
	boolean photoIsthumbnail;
	
	@Column(name="photo_url")
	String photoUrl;
	
}
