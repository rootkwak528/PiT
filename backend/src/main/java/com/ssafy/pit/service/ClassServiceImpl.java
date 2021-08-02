package com.ssafy.pit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Comment;
import com.ssafy.pit.repository.ClassPhotoRepositorySupport;
import com.ssafy.pit.repository.ClassRepository;
import com.ssafy.pit.repository.ClassRepositorySupport;
import com.ssafy.pit.repository.CodeRepositorySupport;
import com.ssafy.pit.repository.CommentRepositorySupport;
import com.ssafy.pit.repository.UserRepository;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.response.ClassDetailGetRes;
import com.ssafy.pit.response.ClassListGetRes;

@Service("classService")
public class ClassServiceImpl implements ClassService {

	@Autowired
	ClassRepository classRepository;

	@Autowired
	UserRepository userRepository;

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
			ClassListGetRes classGetRes = new ClassListGetRes();
			int classNo = classes.getClassNo();
			BeanUtils.copyProperties(classes, classGetRes);
			String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
			if (classThumbnail != null) {
				classGetRes.setClassThumbnail(classThumbnail);
				classListGetRes.add(classGetRes);
			}
		}

		return classListGetRes;
	}

	@Override
	public ClassDetailGetRes getClassDetail(int classNo) {
		Classes classes = classRepositorySupport.getClassDetail(classNo);
		ClassDetailGetRes classDetail = new ClassDetailGetRes();
		// classes -> classDetail로 복제 (title, desc, curri, startdate, enddate, material, tcnt, price, starttime, endtime, teachernmae)
		BeanUtils.copyProperties(classes, classDetail);
		
		// comments 가져오기
		List<Comment> comments = commentRepositorySupport.getCommentList(classNo);
		classDetail.setComments(comments);
		
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

	
	
}
