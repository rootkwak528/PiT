package com.ssafy.pit.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
	Event findUserByEventNo(int eventNo);
	@Transactional
	int deleteByEventNo(int eventNo);
}
