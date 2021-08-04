package com.ssafy.pit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.Classes;

@Repository
public interface ClassRepository extends JpaRepository<Classes, Integer> {
	Classes findClassByClassNo(int classNo);
//	List<Classes> getAllClasses();
}
