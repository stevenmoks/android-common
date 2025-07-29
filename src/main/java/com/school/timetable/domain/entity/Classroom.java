package com.school.timetable.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * 教室实体
 */
@TableName("classroom")
public class Classroom {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;        // 教室名称
    private String building;    // 所在建筑
    private Integer floor;      // 楼层
    private Integer capacity;   // 容量
    private Boolean isLarge;    // 是否为大教室（用于合班上课）
    private String facilities;  // 设施描述（如多媒体、实验设备等）
    
    public Classroom() {}
    
    public Classroom(String name, String building, Integer floor, Integer capacity, Boolean isLarge) {
        this.name = name;
        this.building = building;
        this.floor = floor;
        this.capacity = capacity;
        this.isLarge = isLarge;
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
    
    public String getBuilding() {
        return building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }
    
    public Integer getFloor() {
        return floor;
    }
    
    public void setFloor(Integer floor) {
        this.floor = floor;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public Boolean getIsLarge() {
        return isLarge;
    }
    
    public void setIsLarge(Boolean isLarge) {
        this.isLarge = isLarge;
    }
    
    public String getFacilities() {
        return facilities;
    }
    
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return Objects.equals(id, classroom.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s%d楼, 容量:%d)", name, building, floor, capacity);
    }
}