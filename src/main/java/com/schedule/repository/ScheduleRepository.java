package com.schedule.repository;

import com.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Page<Schedule> findAllByUserId(Long id, Pageable pageable);

}
