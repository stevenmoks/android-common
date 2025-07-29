package com.school.timetable.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.school.timetable.domain.enums.SubjectType;

import java.util.Objects;

/**
 * 学生组实体 - 用于表示不同的选科组合
 */
@TableName("student_group")
public class StudentGroup {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;                  // 组名（如"物化生1组"）
    private String grade;                 // 年级
    private Integer studentCount;         // 学生数量
    private SubjectType firstChoice;      // 首选科目（物理或历史）
    private String secondChoices;         // 再选科目组合（JSON格式，如["化学","生物"]）
    private String description;           // 描述
    
    public StudentGroup() {}
    
    public StudentGroup(String name, String grade, Integer studentCount, SubjectType firstChoice) {
        this.name = name;
        this.grade = grade;
        this.studentCount = studentCount;
        this.firstChoice = firstChoice;
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
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public Integer getStudentCount() {
        return studentCount;
    }
    
    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }
    
    public SubjectType getFirstChoice() {
        return firstChoice;
    }
    
    public void setFirstChoice(SubjectType firstChoice) {
        this.firstChoice = firstChoice;
    }
    
    public String getSecondChoices() {
        return secondChoices;
    }
    
    public void setSecondChoices(String secondChoices) {
        this.secondChoices = secondChoices;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentGroup that = (StudentGroup) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("%s (%s年级, %d人)", name, grade, studentCount);
    }
}