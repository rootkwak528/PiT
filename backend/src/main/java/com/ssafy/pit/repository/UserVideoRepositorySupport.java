package com.ssafy.pit.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.pit.entity.QUserVideo;

@Repository
public class UserVideoRepositorySupport {
	
	@Autowired
	private JPAQueryFactory query;
	
	QUserVideo qUserVideo = QUserVideo.userVideo;
	
	public List<String> getVideoUrls(int userNo, int ptroomNo) {
		List<String> videoUrls = query.select(qUserVideo.videoUrl).from(qUserVideo).where(qUserVideo.user.userNo.eq(userNo), 
				qUserVideo.ptroom.ptroomNo.eq(ptroomNo)).fetch();
		return videoUrls;
	}
}
