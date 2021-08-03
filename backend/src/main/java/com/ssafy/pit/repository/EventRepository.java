package com.ssafy.pit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.pit.entity.Event;
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}
