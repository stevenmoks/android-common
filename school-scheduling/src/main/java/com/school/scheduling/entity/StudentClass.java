package com.school.scheduling.entity;

/**
 * 班级实体类（3+2+1选科组合班级）
 */
public class StudentClass {
    
    private Long id;
    
    /**
     * 班级名称
     */
    private String name;
    
    /**
     * 年级
     */
    private String grade;
    
    /**
     * 学生人数
     */
    private Integer studentCount;
    
    /**
     * 主选科目（物理或历史）
     */
    private Long primarySubjectId;
    
    /**
     * 副选科目（政治、地理、化学、生物选一）
     */
    private Long secondarySubjectId;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    // 构造函数
    public StudentClass() {}
    
    public StudentClass(String name, String grade, Integer studentCount, 
                       Long primarySubjectId, Long secondarySubjectId) {
        this.name = name;
        this.grade = grade;
        this.studentCount = studentCount;
        this.primarySubjectId = primarySubjectId;
        this.secondarySubjectId = secondarySubjectId;
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
    
    public Long getPrimarySubjectId() {
        return primarySubjectId;
    }
    
    public void setPrimarySubjectId(Long primarySubjectId) {
        this.primarySubjectId = primarySubjectId;
    }
    
    public Long getSecondarySubjectId() {
        return secondarySubjectId;
    }
    
    public void setSecondarySubjectId(Long secondarySubjectId) {
        this.secondarySubjectId = secondarySubjectId;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        return "StudentClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade='" + grade + '\'' +
                ", studentCount=" + studentCount +
                ", primarySubjectId=" + primarySubjectId +
                ", secondarySubjectId=" + secondarySubjectId +
                ", enabled=" + enabled +
                '}';
    }
}