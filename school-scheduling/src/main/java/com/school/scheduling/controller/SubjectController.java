package com.school.scheduling.controller;

import com.school.scheduling.entity.Subject;
import com.school.scheduling.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学科管理控制器（简化版）
 */
@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "*")
public class SubjectController {

    @Autowired
    private TimeTableService timeTableService;

    /**
     * 获取所有学科
     */
    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = timeTableService.getCurrentSchedule().getSubjects();
        return ResponseEntity.ok(subjects);
    }

    /**
     * 系统信息
     */
    @GetMapping("/info")
    public ResponseEntity<String> getSystemInfo() {
        return ResponseEntity.ok("高中3+2+1分科排课系统 - 基于Timefold Solver");
    }
}