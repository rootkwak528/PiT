package com.ssafy.pit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.repository.UserRepository;
import com.ssafy.pit.request.UserRegisterPostReq;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User registerUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		
		user.setUserGender(userRegisterInfo.getUserGender());
		user.setUserName(userRegisterInfo.getUserName());
		user.setUserEmail(userRegisterInfo.getUserEmail());
//		user.setUserPwd(userRegisterInfo.getUserPwd());
		user.setUserPwd(passwordEncoder.encode(userRegisterInfo.getUserPwd()));
		user.setUserType(userRegisterInfo.getUserType());
		user.setUserProfile(userRegisterInfo.getUserProfile());
		user.setUserNickname(userRegisterInfo.getUserNickname());
		user.setUserDesc(userRegisterInfo.getUserDesc());
		user.setUserPhone(userRegisterInfo.getUserPhone());
		
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserEmail(String userEmail) {
		User user = userRepository.findUserByUserEmail(userEmail);
		
		return user;
	}
	
	
	@Override
	public User getUserByUserNickname(String userNickname) {
		User user = userRepository.findUserByUserNickname(userNickname);
		return user;
	}
	
}
