package com.school.timetable.controller;

import com.school.timetable.domain.entity.*;
import com.school.timetable.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据管理控制器
 * 管理教师、学生组、教室等基础数据
 */
@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class DataManagementController {
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private StudentGroupMapper studentGroupMapper;
    
    @Autowired
    private ClassroomMapper classroomMapper;
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Autowired
    private LessonMapper lessonMapper;
    
    // ==================== 教师管理 ====================
    
    @GetMapping("/teachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherMapper.selectList(null));
    }
    
    @GetMapping("/teachers/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherMapper.selectById(id);
        return teacher != null ? ResponseEntity.ok(teacher) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/teachers")
    public ResponseEntity<Map<String, Object>> createTeacher(@RequestBody Teacher teacher) {
        int result = teacherMapper.insert(teacher);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "教师创建成功" : "教师创建失败",
            "teacher", teacher
        ));
    }
    
    @PutMapping("/teachers/{id}")
    public ResponseEntity<Map<String, Object>> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        teacher.setId(id);
        int result = teacherMapper.updateById(teacher);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "教师更新成功" : "教师更新失败",
            "teacher", teacher
        ));
    }
    
    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<Map<String, String>> deleteTeacher(@PathVariable Long id) {
        int result = teacherMapper.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", result > 0 ? "true" : "false",
            "message", result > 0 ? "教师删除成功" : "教师删除失败"
        ));
    }
    
    // ==================== 学生组管理 ====================
    
    @GetMapping("/student-groups")
    public ResponseEntity<List<StudentGroup>> getAllStudentGroups() {
        return ResponseEntity.ok(studentGroupMapper.selectList(null));
    }
    
    @GetMapping("/student-groups/{id}")
    public ResponseEntity<StudentGroup> getStudentGroupById(@PathVariable Long id) {
        StudentGroup group = studentGroupMapper.selectById(id);
        return group != null ? ResponseEntity.ok(group) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/student-groups/grade/{grade}")
    public ResponseEntity<List<StudentGroup>> getStudentGroupsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(studentGroupMapper.findByGrade(grade));
    }
    
    @PostMapping("/student-groups")
    public ResponseEntity<Map<String, Object>> createStudentGroup(@RequestBody StudentGroup group) {
        int result = studentGroupMapper.insert(group);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "学生组创建成功" : "学生组创建失败",
            "group", group
        ));
    }
    
    @PutMapping("/student-groups/{id}")
    public ResponseEntity<Map<String, Object>> updateStudentGroup(@PathVariable Long id, @RequestBody StudentGroup group) {
        group.setId(id);
        int result = studentGroupMapper.updateById(group);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "学生组更新成功" : "学生组更新失败",
            "group", group
        ));
    }
    
    @DeleteMapping("/student-groups/{id}")
    public ResponseEntity<Map<String, String>> deleteStudentGroup(@PathVariable Long id) {
        int result = studentGroupMapper.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", result > 0 ? "true" : "false",
            "message", result > 0 ? "学生组删除成功" : "学生组删除失败"
        ));
    }
    
    // ==================== 教室管理 ====================
    
    @GetMapping("/classrooms")
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomMapper.selectList(null));
    }
    
    @GetMapping("/classrooms/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Long id) {
        Classroom classroom = classroomMapper.selectById(id);
        return classroom != null ? ResponseEntity.ok(classroom) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/classrooms/large")
    public ResponseEntity<List<Classroom>> getLargeClassrooms() {
        return ResponseEntity.ok(classroomMapper.findLargeClassrooms());
    }
    
    @GetMapping("/classrooms/regular")
    public ResponseEntity<List<Classroom>> getRegularClassrooms() {
        return ResponseEntity.ok(classroomMapper.findRegularClassrooms());
    }
    
    @PostMapping("/classrooms")
    public ResponseEntity<Map<String, Object>> createClassroom(@RequestBody Classroom classroom) {
        int result = classroomMapper.insert(classroom);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "教室创建成功" : "教室创建失败",
            "classroom", classroom
        ));
    }
    
    @PutMapping("/classrooms/{id}")
    public ResponseEntity<Map<String, Object>> updateClassroom(@PathVariable Long id, @RequestBody Classroom classroom) {
        classroom.setId(id);
        int result = classroomMapper.updateById(classroom);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "教室更新成功" : "教室更新失败",
            "classroom", classroom
        ));
    }
    
    @DeleteMapping("/classrooms/{id}")
    public ResponseEntity<Map<String, String>> deleteClassroom(@PathVariable Long id) {
        int result = classroomMapper.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", result > 0 ? "true" : "false",
            "message", result > 0 ? "教室删除成功" : "教室删除失败"
        ));
    }
    
    // ==================== 时间段管理 ====================
    
    @GetMapping("/time-slots")
    public ResponseEntity<List<TimeSlot>> getAllTimeSlots() {
        return ResponseEntity.ok(timeSlotMapper.selectList(null));
    }
    
    @GetMapping("/time-slots/regular")
    public ResponseEntity<List<TimeSlot>> getRegularTimeSlots() {
        return ResponseEntity.ok(timeSlotMapper.findRegularTimeSlots());
    }
    
    @PostMapping("/time-slots")
    public ResponseEntity<Map<String, Object>> createTimeSlot(@RequestBody TimeSlot timeSlot) {
        int result = timeSlotMapper.insert(timeSlot);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "时间段创建成功" : "时间段创建失败",
            "timeSlot", timeSlot
        ));
    }
    
    // ==================== 课程管理 ====================
    
    @GetMapping("/lessons")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonMapper.selectList(null));
    }
    
    @GetMapping("/lessons/merged")
    public ResponseEntity<List<Lesson>> getMergedLessons() {
        return ResponseEntity.ok(lessonMapper.findMergedClasses());
    }
    
    @PostMapping("/lessons")
    public ResponseEntity<Map<String, Object>> createLesson(@RequestBody Lesson lesson) {
        int result = lessonMapper.insert(lesson);
        return ResponseEntity.ok(Map.of(
            "success", result > 0,
            "message", result > 0 ? "课程创建成功" : "课程创建失败",
            "lesson", lesson
        ));
    }
    
    @DeleteMapping("/lessons/{id}")
    public ResponseEntity<Map<String, String>> deleteLesson(@PathVariable Long id) {
        int result = lessonMapper.deleteById(id);
        return ResponseEntity.ok(Map.of(
            "success", result > 0 ? "true" : "false",
            "message", result > 0 ? "课程删除成功" : "课程删除失败"
        ));
    }
    
    // ==================== 数据初始化 ====================
    
    @PostMapping("/initialize")
    public ResponseEntity<Map<String, String>> initializeData() {
        try {
            // 这里可以添加初始化示例数据的逻辑
            // 例如创建示例教师、学生组、教室等
            
            return ResponseEntity.ok(Map.of(
                "success", "true",
                "message", "数据初始化成功"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "success", "false",
                "message", "数据初始化失败: " + e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearAllData() {
        try {
            lessonMapper.delete(null);
            timeSlotMapper.delete(null);
            classroomMapper.delete(null);
            studentGroupMapper.delete(null);
            teacherMapper.delete(null);
            
            return ResponseEntity.ok(Map.of(
                "success", "true",
                "message", "所有数据已清除"
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "success", "false",
                "message", "数据清除失败: " + e.getMessage()
            ));
        }
    }
}