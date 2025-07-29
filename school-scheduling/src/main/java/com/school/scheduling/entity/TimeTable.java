package com.school.scheduling.entity;

// 暂时注释Timefold相关import
// import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
// import ai.timefold.solver.core.api.domain.solution.PlanningScore;
// import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
// import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
// import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
// import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

/**
 * 课程表解决方案类 - 暂时移除Timefold注解
 * 包含所有问题事实和规划实体
 */
// @PlanningSolution
public class TimeTable {
    
    /**
     * 时间段列表（问题事实）
     */
    // @ProblemFactCollectionProperty
    // @ValueRangeProvider
    private List<TimeSlot> timeSlots;
    
    /**
     * 教师列表（问题事实）
     */
    // @ProblemFactCollectionProperty
    // @ValueRangeProvider
    private List<Teacher> teachers;
    
    /**
     * 学科列表（问题事实）
     */
    // @ProblemFactCollectionProperty
    private List<Subject> subjects;
    
    /**
     * 班级列表（问题事实）
     */
    // @ProblemFactCollectionProperty
    private List<StudentClass> studentClasses;
    
    /**
     * 课程列表（规划实体）
     */
    // @PlanningEntityCollectionProperty
    private List<Lesson> lessons;
    
    /**
     * 分数（由Timefold计算）
     */
    // @PlanningScore
    // private HardSoftScore score;
    private String score; // 暂时用String代替
    
    // 构造函数
    public TimeTable() {}
    
    public TimeTable(List<TimeSlot> timeSlots, List<Teacher> teachers, 
                     List<Subject> subjects, List<StudentClass> studentClasses, 
                     List<Lesson> lessons) {
        this.timeSlots = timeSlots;
        this.teachers = teachers;
        this.subjects = subjects;
        this.studentClasses = studentClasses;
        this.lessons = lessons;
    }
    
    // Getter and Setter
    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
    
    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }
    
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    public List<Subject> getSubjects() {
        return subjects;
    }
    
    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
    
    public List<StudentClass> getStudentClasses() {
        return studentClasses;
    }
    
    public void setStudentClasses(List<StudentClass> studentClasses) {
        this.studentClasses = studentClasses;
    }
    
    public List<Lesson> getLessons() {
        return lessons;
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    public String getScore() {
        return score;
    }
    
    public void setScore(String score) {
        this.score = score;
    }
}