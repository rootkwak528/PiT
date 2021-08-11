package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.Ptroom;
@Repository
public interface PtroomRepository extends JpaRepository<Ptroom, Integer> {
	
}
