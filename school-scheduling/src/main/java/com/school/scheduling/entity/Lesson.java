package com.school.scheduling.entity;

// 暂时注释Timefold相关import
// import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
// import ai.timefold.solver.core.api.domain.variable.PlanningVariable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 课程实体类 - 暂时移除Timefold注解
 * 表示一节具体的课程，需要分配教师和时间段
 */
// @PlanningEntity
@TableName("t_lesson")
public class Lesson {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 学科ID
     */
    private Long subjectId;
    
    /**
     * 班级ID
     */
    private Long studentClassId;
    
    /**
     * 教师ID - 暂时移除规划变量注解
     */
    // @PlanningVariable
    private Long teacherId;
    
    /**
     * 时间段ID - 暂时移除规划变量注解
     */
    // @PlanningVariable
    private Long timeSlotId;
    
    /**
     * 课程状态
     */
    private LessonStatus status;
    
    // 用于Timefold计算的关联对象（非持久化字段）
    private transient Subject subject;
    private transient StudentClass studentClass;
    private transient Teacher teacher;
    private transient TimeSlot timeSlot;
    
    // 枚举定义
    public enum LessonStatus {
        SCHEDULED("已排课"),
        UNSCHEDULED("未排课"),
        CANCELLED("已取消");
        
        private final String description;
        
        LessonStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // 构造函数
    public Lesson() {}
    
    public Lesson(Long subjectId, Long studentClassId) {
        this.subjectId = subjectId;
        this.studentClassId = studentClassId;
        this.status = LessonStatus.UNSCHEDULED;
    }
    
    // Getter and Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSubjectId() {
        return subjectId;
    }
    
    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
    
    public Long getStudentClassId() {
        return studentClassId;
    }
    
    public void setStudentClassId(Long studentClassId) {
        this.studentClassId = studentClassId;
    }
    
    public Long getTeacherId() {
        return teacherId;
    }
    
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
    
    public Long getTimeSlotId() {
        return timeSlotId;
    }
    
    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
    
    public LessonStatus getStatus() {
        return status;
    }
    
    public void setStatus(LessonStatus status) {
        this.status = status;
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    public StudentClass getStudentClass() {
        return studentClass;
    }
    
    public void setStudentClass(StudentClass studentClass) {
        this.studentClass = studentClass;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", studentClassId=" + studentClassId +
                ", teacherId=" + teacherId +
                ", timeSlotId=" + timeSlotId +
                ", status=" + status +
                '}';
    }
}