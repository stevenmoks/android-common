package com.school.scheduling.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * 时间段实体类
 */
@TableName("t_time_slot")
public class TimeSlot {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 星期几
     */
    private DayOfWeek dayOfWeek;
    
    /**
     * 开始时间
     */
    private LocalTime startTime;
    
    /**
     * 结束时间
     */
    private LocalTime endTime;
    
    /**
     * 时间段名称（如：第1节课、第2节课等）
     */
    private String name;
    
    // 构造函数
    public TimeSlot() {}
    
    public TimeSlot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String name) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }
    
    // Getter and Setter
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
    
    @Override
    public String toString() {
        return "TimeSlot{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", name='" + name + '\'' +
                '}';
    }
}