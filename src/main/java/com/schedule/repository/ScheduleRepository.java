package com.schedule.repository;

import com.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByUserId(Long id);

    //TODO 페이징 쿼리 메서드 생성
    Page<Schedule> findAll(Pageable pageable, Sort sort);
}
