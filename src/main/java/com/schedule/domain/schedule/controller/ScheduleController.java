package com.schedule.domain.schedule.controller;

import com.schedule.common.model.CommonResponse;
import com.schedule.domain.schedule.model.dto.CreateScheduleResponse;
import com.schedule.domain.schedule.model.dto.GetSchedulePageResponse;
import com.schedule.domain.schedule.model.dto.GetScheduleResponse;
import com.schedule.domain.schedule.model.dto.UpdateScheduleResponse;
import com.schedule.domain.schedule.model.request.CreateScheduleRequest;
import com.schedule.domain.schedule.model.request.UpdateScheduleRequest;
import com.schedule.domain.schedule.service.ScheduleService;
import com.schedule.domain.user.model.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param sessionUser 세선(id, email)
     * @param request CreateScheduleRequest (title, content)
     * @return CREATED, (id, userId, title, content, createdAt, modifiedAt)
     */
    @PostMapping("/schedules")
    public ResponseEntity<CommonResponse<?>> create(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @RequestBody CreateScheduleRequest request) {

        CommonResponse<?> result = scheduleService.create(sessionUser, request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 일정 페이징 조회
     * @param userId 유저 고유 ID
     * @param pageNumber 페이지 번호
     * @param pageSize 페이지 크기
     * @return OK, (id, userId, title, content, commentCount, createdAt, modifiedAt)
     */
    @GetMapping("/schedules")
    public ResponseEntity<CommonResponse<?>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) int pageNumber,
            @RequestParam(required = false) int pageSize) {

        CommonResponse<?> result = scheduleService.getAll(userId, pageNumber, pageSize);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 일정 조회 단건
     * @param scheduleId 일정 고유 ID
     * @return OK, (id, userId, title, content, createdAt, modifiedAt)
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> getOne(@PathVariable Long scheduleId) {

        CommonResponse<?> result = scheduleService.getOne(scheduleId);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request UpdateScheduleRequest (title, content)
     * @return OK, (id, userId, title, content, createdAt, modifiedAt)
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> update(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {

        CommonResponse<?> result = scheduleService.update(sessionUser, scheduleId, request);

        return ResponseEntity.status(result.getStatus()).body(result);
    }

    /**
     * 일정 삭제
     * @param scheduleId 일정 고유 ID
     * @return NO_CONTENT, null
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<CommonResponse<?>> delete(@SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser, @PathVariable Long scheduleId) {
        CommonResponse<?> result = scheduleService.delete(sessionUser, scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
