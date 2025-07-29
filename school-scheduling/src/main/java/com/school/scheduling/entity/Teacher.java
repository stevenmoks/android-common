package com.school.scheduling.entity;

import java.util.List;

/**
 * 教师实体类
 */
public class Teacher {
    
    private Long id;
    
    /**
     * 教师姓名
     */
    private String name;
    
    /**
     * 教师工号
     */
    private String employeeNumber;
    
    /**
     * 可教授的学科ID列表（JSON格式存储）
     */
    private String subjectIds;
    
    /**
     * 每周最大课时数
     */
    private Integer maxWeeklyHours;
    
    /**
     * 不可用时间段（JSON格式存储时间段信息）
     */
    private String unavailableTimeSlots;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    // 构造函数
    public Teacher() {}
    
    public Teacher(String name, String employeeNumber, String subjectIds, Integer maxWeeklyHours) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.subjectIds = subjectIds;
        this.maxWeeklyHours = maxWeeklyHours;
        this.enabled = true;
    }
    
    public Teacher(String name, String employeeNumber, String subjectIds, Integer maxWeeklyHours, String unavailableTimeSlots) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.subjectIds = subjectIds;
        this.maxWeeklyHours = maxWeeklyHours;
        this.unavailableTimeSlots = unavailableTimeSlots;
        this.enabled = true;
    }
    
    // Getter and Setter
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
    
    public String getEmployeeNumber() {
        return employeeNumber;
    }
    
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    public String getSubjectIds() {
        return subjectIds;
    }
    
    public void setSubjectIds(String subjectIds) {
        this.subjectIds = subjectIds;
    }
    
    public Integer getMaxWeeklyHours() {
        return maxWeeklyHours;
    }
    
    public void setMaxWeeklyHours(Integer maxWeeklyHours) {
        this.maxWeeklyHours = maxWeeklyHours;
    }
    
    public String getUnavailableTimeSlots() {
        return unavailableTimeSlots;
    }
    
    public void setUnavailableTimeSlots(String unavailableTimeSlots) {
        this.unavailableTimeSlots = unavailableTimeSlots;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", subjectIds='" + subjectIds + '\'' +
                ", maxWeeklyHours=" + maxWeeklyHours +
                ", unavailableTimeSlots='" + unavailableTimeSlots + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}