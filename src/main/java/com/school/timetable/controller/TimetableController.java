package com.school.timetable.controller;

import ai.timefold.solver.core.api.solver.SolverStatus;
import com.school.timetable.domain.Timetable;
import com.school.timetable.domain.entity.Lesson;
import com.school.timetable.domain.enums.SubjectType;
import com.school.timetable.service.SubjectCombinationService;
import com.school.timetable.service.TimetableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 排课系统REST API控制器
 */
@RestController
@RequestMapping("/timetable")
@CrossOrigin(origins = "*")
public class TimetableController {
    
    @Autowired
    private TimetableService timetableService;
    
    @Autowired
    private SubjectCombinationService subjectCombinationService;
    
    /**
     * 生成课程表
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateTimetable() {
        String problemId = UUID.randomUUID().toString();
        String jobId = timetableService.generateTimetable(problemId);
        
        return ResponseEntity.ok(Map.of(
            "jobId", jobId,
            "message", "课程表生成任务已提交",
            "status", "SOLVING_ACTIVE"
        ));
    }
    
    /**
     * 获取求解状态
     */
    @GetMapping("/status/{jobId}")
    public ResponseEntity<Map<String, Object>> getSolverStatus(@PathVariable String jobId) {
        SolverStatus status = timetableService.getSolverStatus(jobId);
        
        return ResponseEntity.ok(Map.of(
            "jobId", jobId,
            "status", status.name(),
            "isActive", status.equals(SolverStatus.SOLVING_ACTIVE),
            "description", getStatusDescription(status)
        ));
    }
    
    /**
     * 停止求解
     */
    @PostMapping("/stop/{jobId}")
    public ResponseEntity<Map<String, String>> terminateSolving(@PathVariable String jobId) {
        timetableService.terminateSolving(jobId);
        
        return ResponseEntity.ok(Map.of(
            "jobId", jobId,
            "message", "求解任务已停止"
        ));
    }
    
    /**
     * 获取完整课程表
     */
    @GetMapping("/complete")
    public ResponseEntity<Timetable> getCompleteTimetable() {
        Timetable timetable = timetableService.getCompleteTimetable();
        return ResponseEntity.ok(timetable);
    }
    
    /**
     * 获取学生组课程表
     */
    @GetMapping("/student-group/{groupId}")
    public ResponseEntity<List<Lesson>> getStudentGroupTimetable(@PathVariable Long groupId) {
        List<Lesson> lessons = timetableService.getStudentGroupTimetable(groupId);
        return ResponseEntity.ok(lessons);
    }
    
    /**
     * 获取教师课程表
     */
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Lesson>> getTeacherTimetable(@PathVariable Long teacherId) {
        List<Lesson> lessons = timetableService.getTeacherTimetable(teacherId);
        return ResponseEntity.ok(lessons);
    }
    
    /**
     * 获取所有有效的选科组合
     */
    @GetMapping("/subject-combinations")
    public ResponseEntity<Map<String, Object>> getSubjectCombinations() {
        List<List<SubjectType>> allCombinations = subjectCombinationService.getAllValidCombinations();
        List<List<SubjectType>> physicsCombinations = subjectCombinationService.getPhysicsBasedCombinations();
        List<List<SubjectType>> historyCombinations = subjectCombinationService.getHistoryBasedCombinations();
        
        return ResponseEntity.ok(Map.of(
            "all", allCombinations,
            "physics", physicsCombinations,
            "history", historyCombinations,
            "total", allCombinations.size()
        ));
    }
    
    /**
     * 验证选科组合
     */
    @PostMapping("/validate-combination")
    public ResponseEntity<Map<String, Object>> validateCombination(@RequestBody List<SubjectType> combination) {
        boolean isValid = subjectCombinationService.isValidCombination(combination);
        Map<SubjectType, Integer> weeklyLessons = null;
        
        if (isValid) {
            weeklyLessons = subjectCombinationService.getRecommendedWeeklyLessons(combination);
        }
        
        return ResponseEntity.ok(Map.of(
            "isValid", isValid,
            "combination", combination,
            "weeklyLessons", weeklyLessons != null ? weeklyLessons : Map.of(),
            "message", isValid ? "选科组合有效" : "选科组合无效"
        ));
    }
    
    /**
     * 获取推荐的选科组合
     */
    @GetMapping("/recommendations/{firstChoice}")
    public ResponseEntity<Map<String, Object>> getRecommendations(@PathVariable SubjectType firstChoice) {
        List<SubjectType> recommendations = subjectCombinationService.getRecommendedSecondChoices(firstChoice);
        
        return ResponseEntity.ok(Map.of(
            "firstChoice", firstChoice,
            "recommendations", recommendations,
            "description", firstChoice.getName() + "类推荐组合"
        ));
    }
    
    /**
     * 获取可合班的科目
     */
    @GetMapping("/mergeable-subjects")
    public ResponseEntity<Map<String, Object>> getMergeableSubjects() {
        var mergeableSubjects = subjectCombinationService.getMergeableSubjects();
        
        return ResponseEntity.ok(Map.of(
            "subjects", mergeableSubjects,
            "count", mergeableSubjects.size(),
            "description", "可以合班上课的科目"
        ));
    }
    
    /**
     * 获取系统统计信息
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Timetable timetable = timetableService.getCompleteTimetable();
        
        int totalLessons = timetable.getTotalLessonCount();
        int assignedLessons = timetable.getAssignedLessonCount();
        double assignmentRate = totalLessons > 0 ? (double) assignedLessons / totalLessons * 100 : 0;
        
        return ResponseEntity.ok(Map.of(
            "totalLessons", totalLessons,
            "assignedLessons", assignedLessons,
            "unassignedLessons", totalLessons - assignedLessons,
            "assignmentRate", String.format("%.2f%%", assignmentRate),
            "totalTimeSlots", timetable.getTimeSlots().size(),
            "totalClassrooms", timetable.getClassrooms().size(),
            "totalTeachers", timetable.getTeachers().size(),
            "totalStudentGroups", timetable.getStudentGroups().size(),
            "score", timetable.getScore() != null ? timetable.getScore().toString() : "未计算"
        ));
    }
    
    private String getStatusDescription(SolverStatus status) {
        switch (status) {
            case NOT_SOLVING:
                return "未开始求解";
            case SOLVING_ACTIVE:
                return "正在求解中";
            case SOLVING_SCHEDULED:
                return "求解已调度";
            default:
                return "未知状态";
        }
    }
}