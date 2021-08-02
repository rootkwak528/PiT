package com.ssafy.pit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Code {
	
	@Id
	int codeNo;
	
	String groupCode;
	String code;
	String codeName;
	
}
