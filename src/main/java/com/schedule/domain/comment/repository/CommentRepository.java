package com.schedule.domain.comment.repository;

import com.schedule.common.entity.Schedule;
import com.schedule.domain.comment.model.dto.ScheduleCommentDTO;
import com.schedule.common.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySchedule_Id(Long scheduleId);

    @Query("select c.schedule.id as scheduleId, coalesce(count(c), 0) as count from Comment c group by c.schedule.id")
    List<ScheduleCommentDTO> countCommentByScheduleIdList();

    Long countBySchedule(Schedule schedule);
}
