package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.UserVideo;

@Repository
public interface UserVideoRepository extends JpaRepository<UserVideo, Integer> {

}
