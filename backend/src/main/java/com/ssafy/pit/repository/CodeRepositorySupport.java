package com.ssafy.pit.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.QCode;

@Repository
public class CodeRepositorySupport {

	@Autowired
	private JPAQueryFactory query;
	
	QCode qCode = QCode.code1;
	
	public String getCodeName(String groupCode, String code) {
		String codeName = query.select(qCode.codeName).from(qCode).where(qCode.code.eq(code).and(qCode.groupCode.eq(groupCode))).fetchOne();
		return codeName;
	}
}
