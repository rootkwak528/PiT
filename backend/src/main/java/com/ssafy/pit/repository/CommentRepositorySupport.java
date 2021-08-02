package com.ssafy.pit.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Comment;
import com.ssafy.pit.entity.QClassPhoto;
import com.ssafy.pit.entity.QClasses;
import com.ssafy.pit.entity.QComment;
import com.ssafy.pit.entity.QUserClass;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;

@Repository
public class CommentRepositorySupport {
	
	@Autowired
	private JPAQueryFactory query;
	
	QUserClass qUserClass = QUserClass.userClass;
	QComment qComment = QComment.comment;
	
	public List<Comment> getCommentList(int classNo) {
		List<Comment> commentList = query.selectFrom(qComment).where(qComment.userClass.userClassNo.in(
				JPAExpressions.select(qUserClass.userClassNo).from(qUserClass).where(qUserClass.classes.classNo.eq(classNo))
				)).fetch();
		
		return commentList;
	}
	
	
}