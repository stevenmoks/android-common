package com.school.scheduling.service;

import com.school.scheduling.entity.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * 课程表服务类 - 核心排课业务逻辑（简化版）
 */
@Service
public class TimeTableService {

    /**
     * 生成3+2+1分科排课表（暂时简化实现）
     */
    public CompletableFuture<TimeTable> generateSchedule() {
        // 1. 准备数据
        TimeTable unsolvedTimeTable = prepareTimeTableData();
        
        // 2. 暂时返回未求解的时间表（后续集成Timefold）
        return CompletableFuture.completedFuture(unsolvedTimeTable);
    }

    /**
     * 准备排课数据（简化版）
     */
    private TimeTable prepareTimeTableData() {
        // 创建模拟数据
        List<Subject> subjects = createMockSubjects();
        List<Teacher> teachers = createMockTeachers();
        List<StudentClass> studentClasses = createMockStudentClasses();
        List<TimeSlot> timeSlots = createMockTimeSlots();
        List<Lesson> lessons = new ArrayList<>();
        
        return new TimeTable(timeSlots, teachers, subjects, studentClasses, lessons);
    }

    /**
     * 创建模拟学科数据
     */
    private List<Subject> createMockSubjects() {
        List<Subject> subjects = new ArrayList<>();
        // 3门必选
        subjects.add(new Subject("语文", "CHI", Subject.SubjectType.REQUIRED, 5));
        subjects.add(new Subject("数学", "MATH", Subject.SubjectType.REQUIRED, 5));
        subjects.add(new Subject("英语", "ENG", Subject.SubjectType.REQUIRED, 4));
        // 2门主选
        subjects.add(new Subject("物理", "PHY", Subject.SubjectType.PRIMARY, 4));
        subjects.add(new Subject("历史", "HIS", Subject.SubjectType.PRIMARY, 4));
        // 4门副选
        subjects.add(new Subject("政治", "POL", Subject.SubjectType.SECONDARY, 3));
        subjects.add(new Subject("地理", "GEO", Subject.SubjectType.SECONDARY, 3));
        subjects.add(new Subject("化学", "CHE", Subject.SubjectType.SECONDARY, 3));
        subjects.add(new Subject("生物", "BIO", Subject.SubjectType.SECONDARY, 3));
        return subjects;
    }

    /**
     * 创建模拟教师数据
     */
    private List<Teacher> createMockTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("张老师", "T001", "1,2,3", 20, ""));
        teachers.add(new Teacher("李老师", "T002", "4,5", 18, ""));
        teachers.add(new Teacher("王老师", "T003", "6,7,8,9", 16, ""));
        return teachers;
    }

    /**
     * 创建模拟班级数据
     */
    private List<StudentClass> createMockStudentClasses() {
        List<StudentClass> classes = new ArrayList<>();
        classes.add(new StudentClass("高三1班", "高三", 45, 1L, 6L)); // 物理+政治
        classes.add(new StudentClass("高三2班", "高三", 43, 2L, 7L)); // 历史+地理
        return classes;
    }

    /**
     * 创建模拟时间段数据
     */
    private List<TimeSlot> createMockTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        // 简化：只创建周一到周五，每天8节课
        for (int day = 1; day <= 5; day++) {
            for (int period = 1; period <= 8; period++) {
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setName("第" + period + "节课");
                timeSlots.add(timeSlot);
            }
        }
        return timeSlots;
    }

    /**
     * 获取当前排课状态（简化版）
     */
    public TimeTable getCurrentSchedule() {
        return prepareTimeTableData();
    }

    /**
     * 停止排课求解（暂时简化实现）
     */
    public void stopSolving(UUID problemId) {
        // 暂时不实现，后续集成Timefold时添加
    }
}