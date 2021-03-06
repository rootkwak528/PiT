package com.ssafy.pit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.QClasses;
import com.ssafy.pit.entity.QUserClass;
import com.ssafy.pit.entity.QUserLikes;
import com.ssafy.pit.response.ClassListGetRes;

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
	QUserLikes qUserLikes = QUserLikes.userLikes;
	QUserClass qUserClass = QUserClass.userClass;
	
	
	public List<Classes> getClassList(String classPermission) {
		if (classPermission.equals("000")) {
			return query.selectFrom(qClass).fetch();
		}
		else {			
			return query.selectFrom(qClass).where(qClass.classPermission.eq(classPermission)).fetch();
		}
	}
	
	public Classes getClassDetail(int classNo, String classPermission) {
		Classes classes;
		if (classPermission.equals("000")) {
			classes = query.selectFrom(qClass).where(qClass.classNo.eq(classNo)).fetchOne();
		}
		else {
			classes = query.selectFrom(qClass).where(qClass.classNo.eq(classNo), qClass.classPermission.eq(classPermission)).fetchOne();
		}
		return classes;
	}

	public List<Classes> getClassLikesList(int userNo) {
		List<Classes> classesList = query.selectFrom(qClass).where(qClass.classNo.in(
				JPAExpressions.select(qUserLikes.classes.classNo).from(qUserLikes).where(qUserLikes.user.userNo.eq(userNo))
				), qClass.classPermission.eq("001")).fetch();
		
		return classesList;
	}

	public Integer getUserLikesNo(int userNo, int classNo) {
		Integer userLikesNo = query.select(qUserLikes.userLikesNo).from(qUserLikes).where(qUserLikes.user.userNo.eq(userNo).
				and(qUserLikes.classes.classNo.eq(classNo))).fetchOne();
		return userLikesNo;
	}

	public List<Classes> getUserClassList(int userNo) {
		List<Classes> classesList = query.selectFrom(qClass).where(qClass.classNo.in(
				JPAExpressions.select(qUserClass.classes.classNo).from(qUserClass).where(qUserClass.user.userNo.eq(userNo))
				)).fetch();
		return classesList;
	}

	public int getLastestClassNo() {
		int classNo = query.select(qClass.classNo).from(qClass).orderBy(qClass.classNo.desc()).limit(1).fetchOne();
		return classNo;
	}

	
	public List<Classes> getClassListByTotal(String searchKeyword, String classStartTime, String classEndTime, String permission) {
		List<Classes> classesList = query.selectFrom(qClass).where(
				qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
				qClass.classTitle.contains(searchKeyword).or(qClass.classTeacherName.contains(searchKeyword))
				, qClass.classPermission.eq(permission)).fetch();
		return classesList;
	}
	// getClassListByTotal
//	public List<Classes> getClassListByTotal(String searchKeyword, String classType, String classLevel,
//			String classStartTime, String classEndTime, String permission) {
//		List<Classes> classesList = query.selectFrom(qClass).where(qClass.classType.contains(classType),
//				qClass.classLevel.contains(classLevel), qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
//				qClass.classTitle.contains(searchKeyword).or(qClass.classTeacherName.contains(searchKeyword))
//				, qClass.classPermission.eq(permission)).fetch();
//		return classesList;
//	}

	public List<Classes> getClassListByClassTitle(String searchKeyword, String classStartTime, String classEndTime, String permission) { 
		List<Classes> classesList = query.selectFrom(qClass).where( 
				qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
				qClass.classTitle.contains(searchKeyword)
				, qClass.classPermission.eq(permission)).fetch();
		return classesList;
	}
	// getClassListByClassTitle
//	public List<Classes> getClassListByClassTitle(String searchKeyword, String classType, String classLevel,
//			String classStartTime, String classEndTime, String permission) {
//		List<Classes> classesList = query.selectFrom(qClass).where(qClass.classType.contains(classType), 
//				qClass.classLevel.contains(classLevel), qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
//				qClass.classTitle.contains(searchKeyword)
//				, qClass.classPermission.eq(permission)).fetch();
//		return classesList;
//	}
	
	public List<Classes> getClassListByTrainerName(String searchKeyword, String classStartTime, String classEndTime, String permission) {
		List<Classes> classesList = query.selectFrom(qClass).where( 
				qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
				(qClass.classTeacherName.contains(searchKeyword)),
				qClass.classPermission.eq(permission)).fetch();
		return classesList;
	}
	// getClassListByTrainerName
//	public List<Classes> getClassListByTrainerName(String searchKeyword, String classType, String classLevel,
//			String classStartTime, String classEndTime, String permission) {
//		List<Classes> classesList = query.selectFrom(qClass).where(qClass.classType.contains(classType), 
//				qClass.classLevel.contains(classLevel), qClass.classStartTime.goe(classStartTime), qClass.classEndTime.loe(classEndTime),
//				(qClass.classTeacherName.contains(searchKeyword)),
//				qClass.classPermission.eq(permission)).fetch();
//		return classesList;
//	}
	
	public List<Classes> getTeachClassList(int userNo , String classPermission) {
		return query.selectFrom(qClass).where(qClass.classPermission.eq(classPermission), qClass.user.userNo.eq(userNo)).fetch();
	}
	
	
}
