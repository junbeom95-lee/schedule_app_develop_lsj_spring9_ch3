package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성
     * @param request CreateScheduleRequest (userId, title, content)
     * @return ResponseEntity<CreateScheduleResponse> (id, userId, title, content, createdAt, modifiedAt)
     */
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> create(@RequestBody CreateScheduleRequest request) {

        CreateScheduleResponse result = scheduleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * 일정 페이징 조회
     * @param userId 유저 고유 ID
     * @param pageNumber 페이지 번호
     * @param pageSize 페이지 크기
     * @return esponseEntity<PagedModel<GetSchedulePageResponse>> (id, userId, title, content, commentCount, createdAt, modifiedAt)
     */
    @GetMapping("/schedules")
    public ResponseEntity<PagedModel<GetSchedulePageResponse>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) int pageNumber,
            @RequestParam(required = false) int pageSize) {

        PagedModel<GetSchedulePageResponse> resultList = scheduleService.getAll(userId, pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(resultList);
    }

    /**
     * 일정 조회 단건
     * @param scheduleId 일정 고유 ID
     * @return ResponseEntity<GetOneScheduleResponse> (id, userId, title, content, createdAt, modifiedAt)
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getOne(@PathVariable Long scheduleId) {

        GetScheduleResponse result = scheduleService.getOne(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 일정 수정
     * @param scheduleId 일정 고유 ID
     * @param request UpdateScheduleRequest (title, content)
     * @return ResponseEntity<UpdateScheduleResponse> (id, userId, title, content, createdAt, modifiedAt)
     */
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> update(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {

        UpdateScheduleResponse result = scheduleService.update(scheduleId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 일정 삭제
     * @param scheduleId 일정 고유 ID
     * @return ResponseEntity<Void> NO_CONTENT
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> delete(@PathVariable Long scheduleId) {
        scheduleService.delete(scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
