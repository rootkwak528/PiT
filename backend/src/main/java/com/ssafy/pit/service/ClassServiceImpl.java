package com.ssafy.pit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Comment;
import com.ssafy.pit.entity.User;
import com.ssafy.pit.entity.UserLikes;
import com.ssafy.pit.repository.ClassPhotoRepositorySupport;
import com.ssafy.pit.repository.ClassRepository;
import com.ssafy.pit.repository.ClassRepositorySupport;
import com.ssafy.pit.repository.CodeRepositorySupport;
import com.ssafy.pit.repository.CommentRepositorySupport;
import com.ssafy.pit.repository.UserLikesRepository;
import com.ssafy.pit.repository.UserRepository;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;
import com.ssafy.pit.response.CommentRes;
import com.ssafy.pit.response.RegisterClassGetRes;

@Service("classService")
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository classRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserLikesRepository userLikesRepository;
	
	@Autowired
	ClassRepositorySupport classRepositorySupport;

	@Autowired
	ClassPhotoRepositorySupport classPhotoRepositorySupport;
	
	@Autowired
	CommentRepositorySupport commentRepositorySupport;
	
	@Autowired
	CodeRepositorySupport codeRepositorySupport;
	
	@Override
	public List<ClassListGetRes> getClassList(ClassSearchGetReq searchInfo) {
//		return classRepository.findAll();
		return null;
	}

	@Override
	public List<ClassListGetRes> getClassList() {
		List<Classes> classesList = classRepositorySupport.getClassList();
		List<ClassListGetRes> classListGetRes = new ArrayList();

		for (Classes classes : classesList) {
			if(!classes.getClassPermission().equals("001")) {
				continue;
			}
			
			ClassListGetRes classGetRes = new ClassListGetRes();
			int classNo = classes.getClassNo();
			BeanUtils.copyProperties(classes, classGetRes);
			String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
			if (classThumbnail != null) {
				classGetRes.setClassThumbnail(classThumbnail);
			}
			else {
				classGetRes.setClassThumbnail("");
			}
			classListGetRes.add(classGetRes);

		}
		return classListGetRes;
	}

	@Override
	public ClassDetailGetRes getClassDetail(int classNo) {
		Classes classes = classRepositorySupport.getClassDetail(classNo);
		if(!classes.getClassPermission().equals("001")) {
			return null;
		}
		
		ClassDetailGetRes classDetail = new ClassDetailGetRes();
		// classes -> classDetail로 복제 (title, desc, curri, startdate, enddate, material, tcnt, price, starttime, endtime, teachernmae)
		BeanUtils.copyProperties(classes, classDetail);
		
		// comments 가져오기
		List<Comment> comments = commentRepositorySupport.getCommentList(classNo);
		List <CommentRes> commentReses = new ArrayList<CommentRes>();
		
		for(Comment comment: comments) {
			CommentRes commentRes = new CommentRes();
			commentRes.setCommentWriteDate(comment.getCommentWriteDate());
			commentRes.setCommentContent(comment.getCommentContent());
			String userNickname = comment.getUser().getUserNickname();
			commentRes.setUserNickname(userNickname);
			commentReses.add(commentRes);
		}
		
		classDetail.setComments(commentReses);
		
		// pohot 가져오기
		List<String> photoUrls = classPhotoRepositorySupport.getPhotoList(classNo);
		classDetail.setPhotoUrls(photoUrls);
		String classTypeName = codeRepositorySupport.getCodeName("003", classes.getClassType());
		classDetail.setClassTypeName(classTypeName);
		// 레벨 명 넣기
		String classLevelName = codeRepositorySupport.getCodeName("004", classes.getClassLevel());
		classDetail.setClassLevelName(classLevelName);

		return classDetail;
	}

	@Override
	public List<ClassListGetRes> getClassLikesList(int userNo) {
		List<Classes> classLikesList = classRepositorySupport.getClassLikesList(userNo);
		List<ClassListGetRes> classListGetRes = new ArrayList<ClassListGetRes>();
		
		for (Classes classes : classLikesList) {
			if(!classes.getClassPermission().equals("001")) {
				continue;
			}
			
			ClassListGetRes classGetRes = new ClassListGetRes();
			int classNo = classes.getClassNo();
			BeanUtils.copyProperties(classes, classGetRes);
			String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
			if (classThumbnail != null) {
				classGetRes.setClassThumbnail(classThumbnail);
			}
			else {
				classGetRes.setClassThumbnail("");
			}
			classListGetRes.add(classGetRes);
		}
		
		return classListGetRes;
	}

	@Override
	public int registerClassLikes(int userNo, int classNo) {
		try {			
			User user = userRepository.findUserByUserNo(userNo);
			Classes classes = classRepository.findClassByClassNo(classNo);
			if(!classes.getClassPermission().equals("001")) {
				return 0;
			}
			
			UserLikes userLikes = new UserLikes();
			userLikes.setUser(user);
			userLikes.setClasses(classes);
			userLikesRepository.save(userLikes);
			return 1;
		}
		catch (Exception e) {			
			return 0;
		}
		
	}

	@Override
	public int deleteClassLikes(int userNo, int classNo) {
		int userLikesNo = classRepositorySupport.getUserLikesNo(userNo, classNo);
		return userLikesRepository.deleteByUserLikesNo(userLikesNo);
	}

	@Override
	public List<ClassListGetRes> getFinishedClassList(int userNo) {
		
		try {	
//			Date now = new Date();
//			SimpleDateFormat dateToStringFormat = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<Classes> userClassList = classRepositorySupport.getUserClassList(userNo);
			List<ClassListGetRes> finishedClassList = new ArrayList<ClassListGetRes> ();
			
			
			for (Classes classes : userClassList) {
//				// 날짜비교
//				Date endDate = classes.getClassEndDate();
//				String endTime = classes.getClassEndTime();
//				
//				String endDateString = dateToStringFormat.format(endDate) + " " + endTime + ":00:00";
//				Date classEndDate = stringToDateFormat.parse(endDateString);
//				
//				if(now.compareTo(classEndDate) <= 0) {
//					continue;
//				}
				
				if(classes.getClassTcnt() > classes.getClassCcnt() || !classes.getClassPermission().equals("001")) {
					continue;
				}
				
				ClassListGetRes classGetRes = new ClassListGetRes();
				int classNo = classes.getClassNo();
				BeanUtils.copyProperties(classes, classGetRes);
				String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
				if (classThumbnail != null) {
					classGetRes.setClassThumbnail(classThumbnail);
				}
				else {
					classGetRes.setClassThumbnail("");
				}
				finishedClassList.add(classGetRes);
			}
			return finishedClassList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RegisterClassGetRes> getRegisterClassList(int userNo) {
		try {	
//			Date now = new Date();
//			SimpleDateFormat dateToStringFormat = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<Classes> userClassList = classRepositorySupport.getUserClassList(userNo);
			List<RegisterClassGetRes> registerClassList = new ArrayList<RegisterClassGetRes> ();
			
			
			for (Classes classes : userClassList) {
				// 날짜비교
//				Date endDate = classes.getClassEndDate();
//				Date startDate = classes.getClassStartDate();
//				String endTime = classes.getClassEndTime();
//				String startTime = classes.getClassStartTime();
//				
//				
//				String startDateString = dateToStringFormat.format(startDate) + " " + startTime + ":00:00";
//				String endDateString = dateToStringFormat.format(endDate) + " " + endTime + ":00:00";
//				Date classStartDate = stringToDateFormat.parse(startDateString);
//				Date classEndDate = stringToDateFormat.parse(endDateString);
//				
//				// 현재 날짜가 클래스 수업 마지막 날짜보다 크다면 이미 수강이 완료된 것이므로 Continue
//				if(now.compareTo(classEndDate) > 0) {
//					continue;
//				}
				
				if(classes.getClassTcnt() <= classes.getClassCcnt() || !classes.getClassPermission().equals("001")) {
					continue;
				}
				
				RegisterClassGetRes registerClass = new RegisterClassGetRes();
				int classNo = classes.getClassNo();
				BeanUtils.copyProperties(classes, registerClass);
				String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
				if (classThumbnail != null) {
					registerClass.setClassThumbnail(classThumbnail);
				}
				else {
					registerClass.setClassThumbnail("");
				}
				float totalCnt = classes.getClassTcnt();
				float classCnt = classes.getClassCcnt();
				float classPercentage = Math.round((classCnt/totalCnt)*10000) / (float) 100.0;
				
				registerClass.setClassPercentage(classPercentage);
				
				registerClassList.add(registerClass);
			}
			return registerClassList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
