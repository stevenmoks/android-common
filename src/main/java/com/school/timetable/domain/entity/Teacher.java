package com.school.timetable.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.school.timetable.domain.enums.SubjectType;

import java.util.Objects;

/**
 * 教师实体
 */
@TableName("teacher")
public class Teacher {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;                    // 姓名
    private String employeeId;              // 工号
    private SubjectType primarySubject;     // 主要任教科目
    private String secondarySubjects;       // 次要任教科目（JSON格式存储）
    private Integer maxDailyLessons;        // 每日最大课时数
    private Integer maxWeeklyLessons;       // 每周最大课时数
    private String unavailableTimeSlots;    // 不可用时间段（JSON格式存储）
    private Boolean isHeadTeacher;          // 是否为班主任
    
    public Teacher() {}
    
    public Teacher(String name, String employeeId, SubjectType primarySubject) {
        this.name = name;
        this.employeeId = employeeId;
        this.primarySubject = primarySubject;
        this.maxDailyLessons = 8;  // 默认每日最多8节课
        this.maxWeeklyLessons = 24; // 默认每周最多24节课
        this.isHeadTeacher = false;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public SubjectType getPrimarySubject() {
        return primarySubject;
    }
    
    public void setPrimarySubject(SubjectType primarySubject) {
        this.primarySubject = primarySubject;
    }
    
    public String getSecondarySubjects() {
        return secondarySubjects;
    }
    
    public void setSecondarySubjects(String secondarySubjects) {
        this.secondarySubjects = secondarySubjects;
    }
    
    public Integer getMaxDailyLessons() {
        return maxDailyLessons;
    }
    
    public void setMaxDailyLessons(Integer maxDailyLessons) {
        this.maxDailyLessons = maxDailyLessons;
    }
    
    public Integer getMaxWeeklyLessons() {
        return maxWeeklyLessons;
    }
    
    public void setMaxWeeklyLessons(Integer maxWeeklyLessons) {
        this.maxWeeklyLessons = maxWeeklyLessons;
    }
    
    public String getUnavailableTimeSlots() {
        return unavailableTimeSlots;
    }
    
    public void setUnavailableTimeSlots(String unavailableTimeSlots) {
        this.unavailableTimeSlots = unavailableTimeSlots;
    }
    
    public Boolean getIsHeadTeacher() {
        return isHeadTeacher;
    }
    
    public void setIsHeadTeacher(Boolean isHeadTeacher) {
        this.isHeadTeacher = isHeadTeacher;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, employeeId, primarySubject.getName());
    }
}