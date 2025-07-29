package com.school.timetable.domain.entity;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.school.timetable.domain.enums.SubjectType;

import java.util.Objects;

/**
 * 课程实体 - Timefold规划实体
 */
@PlanningEntity
@TableName("lesson")
public class Lesson {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private SubjectType subject;          // 科目
    private Long teacherId;               // 教师ID
    private Long studentGroupId;          // 学生组ID
    private Integer weeklyLessons;        // 每周课时数
    private Boolean requiresLargeRoom;    // 是否需要大教室（用于合班）
    private String mergedGroups;          // 合班组合（JSON格式，存储合班的学生组ID）
    
    // Timefold规划变量
    @PlanningVariable
    @TableField(exist = false)
    private TimeSlot timeSlot;            // 分配的时间段
    
    @PlanningVariable
    @TableField(exist = false)
    private Classroom classroom;          // 分配的教室
    
    // 关联对象（不存储在数据库中）
    @TableField(exist = false)
    private Teacher teacher;
    
    @TableField(exist = false)
    private StudentGroup studentGroup;
    
    public Lesson() {}
    
    public Lesson(SubjectType subject, Long teacherId, Long studentGroupId, Integer weeklyLessons) {
        this.subject = subject;
        this.teacherId = teacherId;
        this.studentGroupId = studentGroupId;
        this.weeklyLessons = weeklyLessons;
        this.requiresLargeRoom = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public SubjectType getSubject() {
        return subject;
    }
    
    public void setSubject(SubjectType subject) {
        this.subject = subject;
    }
    
    public Long getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    
    public Long getStudentGroupId() {
        return studentGroupId;
    }
    
    public void setStudentGroupId(Long studentGroupId) {
        this.studentGroupId = studentGroupId;
    }
    
    public Integer getWeeklyLessons() {
        return weeklyLessons;
    }
    
    public void setWeeklyLessons(Integer weeklyLessons) {
        this.weeklyLessons = weeklyLessons;
    }
    
    public Boolean getRequiresLargeRoom() {
        return requiresLargeRoom;
    }
    
    public void setRequiresLargeRoom(Boolean requiresLargeRoom) {
        this.requiresLargeRoom = requiresLargeRoom;
    }
    
    public String getMergedGroups() {
        return mergedGroups;
    }
    
    public void setMergedGroups(String mergedGroups) {
        this.mergedGroups = mergedGroups;
    }
    
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public Classroom getClassroom() {
        return classroom;
    }
    
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public StudentGroup getStudentGroup() {
        return studentGroup;
    }
    
    public void setStudentGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }
    
    /**
     * 检查是否为合班课程
     */
    public boolean isMergedClass() {
        return mergedGroups != null && !mergedGroups.trim().isEmpty();
    }
    
    /**
     * 检查是否需要特殊时间安排（早读、晚自习）
     */
    public boolean requiresSpecialTimeSlot() {
        return subject == SubjectType.MORNING_READING || subject == SubjectType.EVENING_STUDY;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s", 
            subject != null ? subject.getName() : "未知科目",
            studentGroup != null ? studentGroup.getName() : "未知班级");
    }
}