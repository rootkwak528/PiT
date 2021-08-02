package com.ssafy.pit.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.pit.entity.User;
import com.ssafy.pit.service.UserService;

// 현재 액세스 토큰으로부터 인증된 유저의 상세정보(활성화 여브 만료, 롤, 유저정보 등) 관련 서비스 정의

@Service
public class PitUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userService.getUserByUserEmail(userEmail);
		if(user != null) {
			PitUserDetails pitDetails = new PitUserDetails(user);
			return pitDetails;
		}
		
		return null;
	}
	
	
}
