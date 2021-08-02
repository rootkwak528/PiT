package com.ssafy.pit.repository;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Comment;
import com.ssafy.pit.entity.QClassPhoto;
import com.ssafy.pit.entity.QClasses;
import com.ssafy.pit.response.ClassDetailGetRes;

@Repository
public class ClassRepositorySupport {
	
	@Autowired
	private JPAQueryFactory query;
	
	@Autowired
	private CommentRepositorySupport commentRepositorySupport;
	
	@Autowired
	private ClassPhotoRepositorySupport classPhotoRepositorySupprot;
	
	@Autowired
	private CodeRepositorySupport codeRepositorySupport;
	
	QClasses qClass = QClasses.classes;
	
	public List<Classes> getClassList() {
		return query.selectFrom(qClass).fetch();
		
	}
	
	public Classes getClassDetail(int classNo) {
		Classes classes = query.selectFrom(qClass).where(qClass.classNo.eq(classNo)).fetchOne();
		return classes;
	}
	

	
	
}
