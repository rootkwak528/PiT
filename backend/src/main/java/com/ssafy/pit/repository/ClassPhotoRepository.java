package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.ClassPhoto;

@Repository
public interface ClassPhotoRepository extends JpaRepository<ClassPhoto, Integer> {
//	Classes findClassByClassNo(int classNo);
//	List<Classes> getAllClasses();
}
