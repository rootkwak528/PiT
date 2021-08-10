package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.UserClass;

@Repository
public interface UserClassRepository extends JpaRepository<UserClass, Integer> {
}
