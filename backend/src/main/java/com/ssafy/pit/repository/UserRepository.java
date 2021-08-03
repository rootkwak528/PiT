package com.ssafy.pit.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findUserByUserEmail(String userEmail);
	User findUserByUserNickname(String userNickname);
	@Transactional
	int deleteByUserEmail(String userEmail);
}
