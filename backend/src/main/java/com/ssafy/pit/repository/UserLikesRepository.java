package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.pit.entity.UserLikes;

@Repository
public interface UserLikesRepository extends JpaRepository<UserLikes, Integer> {
	UserLikes findByUserLikesNo(int userLikesNo);
	@Transactional
	int deleteByUserLikesNo(int userLikesNo);
}
