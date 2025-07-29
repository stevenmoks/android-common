package com.school.scheduling.controller;

import com.school.scheduling.entity.StudentClass;
import com.school.scheduling.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理控制器（简化版）
 */
@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*")
public class StudentClassController {

    @Autowired
    private TimeTableService timeTableService;

    /**
     * 获取所有班级
     */
    @GetMapping
    public ResponseEntity<List<StudentClass>> getAllClasses() {
        List<StudentClass> classes = timeTableService.getCurrentSchedule().getStudentClasses();
        return ResponseEntity.ok(classes);
    }
}