package com.school.timetable.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.school.timetable.domain.enums.TimeSlotType;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

/**
 * 时间段实体
 */
@TableName("time_slot")
public class TimeSlot {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private DayOfWeek dayOfWeek;  // 星期几
    private LocalTime startTime;  // 开始时间
    private LocalTime endTime;    // 结束时间
    private String name;          // 时间段名称（如"第一节课"）
    private TimeSlotType type;    // 时间段类型
    
    public TimeSlot() {}
    
    public TimeSlot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String name, TimeSlotType type) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.type = type;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public TimeSlotType getType() {
        return type;
    }
    
    public void setType(TimeSlotType type) {
        this.type = type;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return Objects.equals(id, timeSlot.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s %s (%s-%s)", 
            dayOfWeek.name(), name, startTime, endTime);
    }
}