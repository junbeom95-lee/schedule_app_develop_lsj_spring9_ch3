package com.schedule.controller;

import com.schedule.dto.*;
import com.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
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
     * 일정 조회 다건
     * @param userId 유저 고유 ID
     * @return ResponseEntity<List<GetScheduleResponse>> (id, userId, title, content, createdAt, modifiedAt)
     */
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAll(@RequestParam(required = false) Long userId) {

        List<GetScheduleResponse> resultList = scheduleService.getAll(userId);

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
