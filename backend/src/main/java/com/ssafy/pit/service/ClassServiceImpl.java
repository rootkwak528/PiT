package com.ssafy.pit.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ssafy.pit.entity.ClassPhoto;
import com.ssafy.pit.entity.Classes;
import com.ssafy.pit.entity.Comment;
import com.ssafy.pit.entity.Ptroom;
import com.ssafy.pit.entity.User;
import com.ssafy.pit.entity.UserClass;
import com.ssafy.pit.entity.UserLikes;
import com.ssafy.pit.entity.UserVideo;
import com.ssafy.pit.repository.ClassPhotoRepository;
import com.ssafy.pit.repository.ClassPhotoRepositorySupport;
import com.ssafy.pit.repository.ClassRepository;
import com.ssafy.pit.repository.ClassRepositorySupport;
import com.ssafy.pit.repository.CodeRepositorySupport;
import com.ssafy.pit.repository.CommentRepositorySupport;
import com.ssafy.pit.repository.PtroomRepositorySupport;
import com.ssafy.pit.repository.UserClassRepository;
import com.ssafy.pit.repository.UserLikesRepository;
import com.ssafy.pit.repository.UserRepository;
import com.ssafy.pit.repository.UserVideoRepository;
import com.ssafy.pit.repository.UserVideoRepositorySupport;
import com.ssafy.pit.request.ClassSearchGetReq;
import com.ssafy.pit.request.CreateClassPostReq;
import com.ssafy.pit.request.SetVideoUrlsPostReq;
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
	
	@Autowired
	ClassPhotoRepository classPhotoRepository;
	
	@Autowired
	UserClassRepository userClassRepository;
	
	@Autowired
	PtroomRepositorySupport ptroomRepositorySupport;
	
	@Autowired
	UserVideoRepository userVideoRepository;
	
	@Autowired
	UserVideoRepositorySupport userVideoRepositorySupport;
	
	// 이미지 생성폴더 이름
	String uploadFolder = "upload";
	// 자기 이미지 생성할 경로
//	String uploadPath = "C:" + File.separator + "Users" + File.separator + "ahnda" + File.separator
//			+ "ssafy5-study" + File.separator + "Second" + File.separator + "Projects" + File.separator + "CommonProject" 
//			+ File.separator + "S05P13A204" + File.separator + "backend" + File.separator + "src" + File.separator + "main"
//			+ File.separator + "resources" + File.separator + "static";
	
//	/Users/seoyoseb/SSAFYProjects/S05P13A204/backend/src/main/resources/static
//  서버 이미지 저장될 경로
//	/home/ubuntu/S05P13A204/backend/src/main/resources/static
	
	String uploadPath = "/Users" + File.separator + "seoyoseb" + File.separator + "SSAFYProjects"
    		+ File.separator + "S05P13A204"
            + File.separator + "backend" 
            + File.separator + "src" 
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static";
	
	
	@Override
	public List<ClassListGetRes> getClassList(ClassSearchGetReq searchInfo, String permission) {
		String searchType = searchInfo.getSearchType();  //서치타입 (0(선택안한경우 전체검색) , 1(클래스명 검색)), 2(강사명 검색)
		String searchKeyword = searchInfo.getSearchKeyword();  //검색키워드 
		String classType = searchInfo.getClassType();  //클래스타입 (001, 002, 003, 004) 전체(선택안한경우)는 00
		String classLevel = searchInfo.getClassLevel();  //레벨코드 (001, 002, 003, 004) 전체(선택안한경우)는 00
		String classDay = searchInfo.getClassDay();  //수업요일 (화목)  화, 목 "화목" 전체(월화수목금토일)
		String classStartTime = searchInfo.getClassStartTime();  //시작시간 "09"
		String classEndTime = searchInfo.getClassEndTime();  //종료시간 "12"
		
//		if (searchKeyword == "    ") {
//		}
		searchKeyword = searchKeyword.trim();
		System.out.println("searchKeyword :"+ searchKeyword.trim());
			
		System.out.println("classDay : "+ classDay);
		
		List<Classes> classesList = new ArrayList<Classes>();
		List<ClassListGetRes> classListGetRes = new ArrayList();
		// 조건 없는 경우, 전체검색
		if (searchType.equals("0")) {
			classesList = classRepositorySupport.getClassListByTotal(searchKeyword, classStartTime, classEndTime, permission);
//			classesList = classRepositorySupport.getClassListByTotal(searchKeyword, classType, classLevel, classStartTime, classEndTime, permission);
		}
		// 클래스명으로 검색
		else if (searchType.equals("1")) {
			classesList = classRepositorySupport.getClassListByClassTitle(searchKeyword, classStartTime, classEndTime, permission);
//			classesList = classRepositorySupport.getClassListByClassTitle(searchKeyword, classType, classLevel, classStartTime, classEndTime, permission);
		}
		// 강사명 검색
		else if (searchType.equals("2")){
			classesList = classRepositorySupport.getClassListByTrainerName(searchKeyword, classStartTime, classEndTime, permission);
//			classesList = classRepositorySupport.getClassListByTrainerName(searchKeyword, classType, classLevel, classStartTime, classEndTime, permission);
		}
		
		for(Classes classes: classesList) {
			ClassListGetRes classGetRes = new ClassListGetRes();
			// 클래스 요일은 query로 작성하기 힘들어서 자바에서 처리를 해줌
			String dbClassDay = classes.getClassDay();
			String[] days = classDay.split("");				// 입력받은 요일들을 배열로 하나씩 받는다.
			int dayCnt = 0;									// 
			for(int i=0, size=days.length; i<size; i++) {
				String day = days[i];
				if(dbClassDay.contains(day)) {
					dayCnt++;
				}
			}
			
			if(dayCnt == 0) continue;
			
			String dbClassType = classes.getClassType();
			String[] types = classType.split(" ");
			int typeCnt = 0;
			for (int i = 0, size=types.length; i<size; i++) {
				String type = types[i];
				if(dbClassType.contains(type)) {
					typeCnt++;
				}
			}
			
			if(typeCnt == 0) continue;
			
			String dbClassLevel = classes.getClassLevel();
			String[] levels = classLevel.split(" ");
			int levelCnt = 0;
			for (int i = 0, size=levels.length; i < size; i++) {
				String level = levels[i];
				if(dbClassLevel.contains(level)) {
					levelCnt++;
				}
			}
			
			if(levelCnt == 0) continue;
			
			int classNo = classes.getClassNo();
			BeanUtils.copyProperties(classes, classGetRes);
			String classThumbnail = classPhotoRepositorySupport.getThumbnail(classNo);
			if (classThumbnail != null) {
				classGetRes.setClassThumbnail(classThumbnail);
			}
			else {
				classGetRes.setClassThumbnail("");
			}
			classListGetRes.add(classGetRes); // 조회해야할 클래스를 추가
		}
		
		return classListGetRes;
	}

	@Override
	public List<ClassListGetRes> getClassList(String permission) {
		List<Classes> classesList = classRepositorySupport.getClassList(permission);
		List<ClassListGetRes> classListGetRes = new ArrayList();
		for (Classes classes : classesList) {
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
	public ClassDetailGetRes getClassDetail(int classNo, String permission) {
		Classes classes = classRepositorySupport.getClassDetail(classNo, permission);
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
				return 2;
			}
			
			if (classRepositorySupport.getUserLikesNo(userNo, classNo) != null) {
				return 3;
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
				registerClass.setClassType(classes.getClassType());
				
				registerClass.setUserNo(classes.getUser().getUserNo());
				registerClassList.add(registerClass);
			}
			return registerClassList;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void createClass(CreateClassPostReq createClassInfo, User user) throws Exception {
		Classes classes = new Classes();
		BeanUtils.copyProperties(createClassInfo, classes);
		SimpleDateFormat dateToStringFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = createClassInfo.getStartDate();
		String endDate = createClassInfo.getEndDate();
		
		Date classStartDate = dateToStringFormat.parse(startDate);
		Date classEndDate = dateToStringFormat.parse(endDate);
		
		classes.setClassStartDate(classStartDate);
		classes.setClassEndDate(classEndDate);
		classes.setUser(user);
		classes.setClassTeacherName(user.getUserName());
		classes.setClassUcnt(0);
		classes.setClassCcnt(0);
		classes.setClassPermission("002");
		classRepository.save(classes);
		return;
	}

	@Override
	public int getLatestClassNo() throws Exception {
		int classNo = classRepositorySupport.getLastestClassNo();
		return classNo;
	}

	@Override
	public void createClassPhoto(MultipartHttpServletRequest request, int classNo)
			throws Exception {
		
		try {
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if(!uploadDir.exists()) uploadDir.mkdir();
			
			MultipartFile part = request.getFile("classThumbnail");
			String fileName = part.getOriginalFilename();
			UUID uuid = UUID.randomUUID();
			String extension = FilenameUtils.getExtension(fileName);
			String savingFileName = uuid + "." + extension;
			File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
			part.transferTo(destFile);
			String fileUrl = "http://localhost:8080/static/" + uploadFolder + "/" + savingFileName;
			System.out.println("fileUrl : "+ fileUrl);
			
			ClassPhoto classPhoto = new ClassPhoto();
			Classes classes = classRepository.findClassByClassNo(classNo);
			classPhoto.setClasses(classes);
			classPhoto.setPhotoIsthumbnail(true);
			classPhoto.setPhotoUrl(fileUrl);
			
			classPhotoRepository.save(classPhoto);
			return;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public int enrollClass(User user, int classNo) throws Exception {
		Classes classes = classRepository.findClassByClassNo(classNo);
		if(classes.getClassUcnt() < classes.getClassLimit()) {
			if(classes.getClassPermission().equals("001")) {
				UserClass userClass = new UserClass();
				userClass.setUser(user);
				userClass.setClasses(classes);
				classes.setClassUcnt(classes.getClassUcnt() + 1);
				classRepository.save(classes);
				userClassRepository.save(userClass);
				return 1;				
			}
			else {
				return 2;				
			}
		}
		else {
			return 0;
		}
	}

	@Override
	public void updateClassPermission(int classNo, String permission) throws Exception {
		Classes classes = classRepository.findClassByClassNo(classNo);
		classes.setClassPermission(permission);
		classRepository.save(classes);
		return;
	}
	
	@Override
	public void setVideoUrls(int userNo, int classNo, SetVideoUrlsPostReq setVideoUrlsInfo) throws Exception {
		UserVideo userVideo = new UserVideo();
		User user = userRepository.findUserByUserNo(userNo);
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		BeanUtils.copyProperties(setVideoUrlsInfo, userVideo);
		userVideo.setUser(user);
		userVideo.setPtroom(ptroom);
//		userVideo.setVideoUrl(videoUrl);
		userVideoRepository.save(userVideo);
		return;
	}

	@Override
	public List<String> getVideoUrls(int userNo, int classNo) throws Exception {
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		int ptroomNo = ptroom.getPtroomNo();
		List<String> videoUrls = userVideoRepositorySupport.getVideoUrls(userNo, ptroomNo);
		return videoUrls;
	}
	

	@Override
	public List<Date> getSaveTimtes(int userNo, int classNo) throws Exception {
		Ptroom ptroom = ptroomRepositorySupport.getPtroomByClassNo(classNo);
		int ptroomNo = ptroom.getPtroomNo();
		List<Date> videoSaveTime = userVideoRepositorySupport.getSaveTimtes(userNo, ptroomNo);
		return videoSaveTime;
	}

	@Override
	public List<RegisterClassGetRes> getTeachClassList(int userNo) {
		List<Classes> classList = classRepositorySupport.getTeachClassList(userNo, "001");
		List<RegisterClassGetRes> teachClassList = new ArrayList<RegisterClassGetRes>();
		
		for (Classes classes : classList) {
			if(classes.getClassTcnt() <= classes.getClassCcnt() || !classes.getClassPermission().equals("001")) {
				continue;
			}
			
			int classNo = classes.getClassNo();
			
			RegisterClassGetRes registerClass = new RegisterClassGetRes();
			
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
			
			registerClass.setUserNo(classes.getUser().getUserNo());
			registerClass.setClassType(classes.getClassType());
			
			teachClassList.add(registerClass);
		}
		return teachClassList;
	}

	@Override
	public List<ClassListGetRes> getFinishedTeachClassList(int userNo) {
		List<Classes> classList = classRepositorySupport.getTeachClassList(userNo, "001");
		List<ClassListGetRes> teachFinishedClassList = new ArrayList<ClassListGetRes>();
		System.out.println(teachFinishedClassList.size());
		for (Classes classes : classList) {
			
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
			teachFinishedClassList.add(classGetRes);
		}
		return teachFinishedClassList;
	}

	@Override
	public void addClassCnt(int classNo) {
		Classes classes = classRepository.findClassByClassNo(classNo);
		classes.setClassCcnt(classes.getClassCcnt()+1);
		classRepository.save(classes);
		return;
	}

	@Override
	public int createSubPhotos(MultipartHttpServletRequest request, int classNo) {
		try {
			File uploadDir = new File(uploadPath + File.separator + uploadFolder);
			if(!uploadDir.exists()) uploadDir.mkdir();
			
			List<MultipartFile> parts = request.getFiles("classSubPhotos");
			System.out.println(parts.size());
			
			if(parts.size() == 0) {
				return 2;
			}
			
			for(MultipartFile part: parts) {
				String fileName = part.getOriginalFilename();
				UUID uuid = UUID.randomUUID();
				String extension = FilenameUtils.getExtension(fileName);
				String savingFileName = uuid + "." + extension;
				File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
				part.transferTo(destFile);
				String fileUrl = "http://localhost:8080/static/" + uploadFolder + "/" + savingFileName;
				System.out.println("fileUrl : "+ fileUrl);
				
				ClassPhoto classPhoto = new ClassPhoto();
				Classes classes = classRepository.findClassByClassNo(classNo);
				classPhoto.setClasses(classes);
				classPhoto.setPhotoIsthumbnail(false);
				classPhoto.setPhotoUrl(fileUrl);
				classPhotoRepository.save(classPhoto);
			}
			
			return 1;
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}	
}
