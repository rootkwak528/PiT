package com.ssafy.pit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.QClassPhoto;
import com.ssafy.pit.entity.QComment;
import com.ssafy.pit.entity.QUserClass;

@Repository
public class ClassPhotoRepositorySupport {
	
	@Autowired
	private JPAQueryFactory query;
	
	QUserClass qUserClass = QUserClass.userClass;
	QComment qComment = QComment.comment;
	QClassPhoto qClassPhoto = QClassPhoto.classPhoto;
	
	public List<String> getPhotoList(int classNo) {
		List<String> photoUrls = query.select(qClassPhoto.photoUrl).from(qClassPhoto).
				where(qClassPhoto.classes.classNo.eq(classNo)).fetch();
		return photoUrls;
	}

	public String getThumbnail(int classNo) {
		String classThumbnail = query.select(qClassPhoto.photoUrl).
				from(qClassPhoto).where(qClassPhoto.photoIsthumbnail.
						eq(true), qClassPhoto.classes.classNo.eq(classNo)).fetchOne();
		return classThumbnail;
	}
}
