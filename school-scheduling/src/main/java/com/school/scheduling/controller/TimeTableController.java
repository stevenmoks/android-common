package com.school.scheduling.controller;

import com.school.scheduling.entity.TimeTable;
import com.school.scheduling.service.TimeTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 课程表控制器 - 提供排课相关API
 */
@RestController
@RequestMapping("/api/timetable")
@CrossOrigin(origins = "*")
public class TimeTableController {

    @Autowired
    private TimeTableService timeTableService;

    /**
     * 启动排课求解
     */
    @PostMapping("/solve")
    public ResponseEntity<String> startSolving() {
        try {
            CompletableFuture<TimeTable> future = timeTableService.generateSchedule();
            return ResponseEntity.ok("排课任务已启动，正在求解中...");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("启动排课失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前排课结果
     */
    @GetMapping("/current")
    public ResponseEntity<TimeTable> getCurrentSchedule() {
        try {
            TimeTable timeTable = timeTableService.getCurrentSchedule();
            return ResponseEntity.ok(timeTable);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 停止排课求解
     */
    @PostMapping("/stop/{problemId}")
    public ResponseEntity<String> stopSolving(@PathVariable UUID problemId) {
        try {
            timeTableService.stopSolving(problemId);
            return ResponseEntity.ok("排课求解已停止");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("停止排课失败: " + e.getMessage());
        }
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("排课系统运行正常");
    }
}