package com.school.timetable.domain;

import ai.timefold.solver.core.api.domain.solution.PlanningEntityCollectionProperty;
import ai.timefold.solver.core.api.domain.solution.PlanningScore;
import ai.timefold.solver.core.api.domain.solution.PlanningSolution;
import ai.timefold.solver.core.api.domain.solution.ProblemFactCollectionProperty;
import ai.timefold.solver.core.api.domain.valuerange.ValueRangeProvider;
import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import com.school.timetable.domain.entity.Classroom;
import com.school.timetable.domain.entity.Lesson;
import com.school.timetable.domain.entity.StudentGroup;
import com.school.timetable.domain.entity.Teacher;
import com.school.timetable.domain.entity.TimeSlot;

import java.util.List;

/**
 * 课程表解决方案类
 * 这是Timefold求解器的主要输入和输出对象
 */
@PlanningSolution
public class Timetable {
    
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<TimeSlot> timeSlots;
    
    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Classroom> classrooms;
    
    @ProblemFactCollectionProperty
    private List<Teacher> teachers;
    
    @ProblemFactCollectionProperty
    private List<StudentGroup> studentGroups;
    
    @PlanningEntityCollectionProperty
    private List<Lesson> lessons;
    
    @PlanningScore
    private HardSoftScore score;
    
    public Timetable() {}
    
    public Timetable(List<TimeSlot> timeSlots, List<Classroom> classrooms, 
                     List<Teacher> teachers, List<StudentGroup> studentGroups, 
                     List<Lesson> lessons) {
        this.timeSlots = timeSlots;
        this.classrooms = classrooms;
        this.teachers = teachers;
        this.studentGroups = studentGroups;
        this.lessons = lessons;
    }
    
    // Getters and Setters
    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
    
    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
    
    public List<Classroom> getClassrooms() {
        return classrooms;
    }
    
    public void setClassrooms(List<Classroom> classrooms) {
        this.classrooms = classrooms;
    }
    
    public List<Teacher> getTeachers() {
        return teachers;
    }
    
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    
    public List<StudentGroup> getStudentGroups() {
        return studentGroups;
    }
    
    public void setStudentGroups(List<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }
    
    public List<Lesson> getLessons() {
        return lessons;
    }
    
    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    
    public HardSoftScore getScore() {
        return score;
    }
    
    public void setScore(HardSoftScore score) {
        this.score = score;
    }
    
    /**
     * 获取已分配的课程数量
     */
    public int getAssignedLessonCount() {
        if (lessons == null) return 0;
        return (int) lessons.stream()
            .filter(lesson -> lesson.getTimeSlot() != null && lesson.getClassroom() != null)
            .count();
    }
    
    /**
     * 获取总课程数量
     */
    public int getTotalLessonCount() {
        return lessons != null ? lessons.size() : 0;
    }
}