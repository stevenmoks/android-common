package com.school.timetable.domain.enums;

/**
 * 时间段类型枚举
 */
public enum TimeSlotType {
    MORNING_READING("早读"),
    REGULAR("常规课程"),
    EVENING_STUDY("晚自习"),
    BREAK("休息时间"),
    LUNCH_BREAK("午休");
    
    private final String description;
    
    TimeSlotType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}