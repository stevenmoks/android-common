package com.school.timetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.timetable.domain.entity.StudentGroup;
import com.school.timetable.domain.enums.SubjectType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 学生组Mapper接口
 */
@Mapper
public interface StudentGroupMapper extends BaseMapper<StudentGroup> {
    
    /**
     * 根据年级查找学生组
     */
    @Select("SELECT * FROM student_group WHERE grade = #{grade}")
    List<StudentGroup> findByGrade(String grade);
    
    /**
     * 根据首选科目查找学生组
     */
    @Select("SELECT * FROM student_group WHERE first_choice = #{firstChoice}")
    List<StudentGroup> findByFirstChoice(SubjectType firstChoice);
    
    /**
     * 查找物理类学生组
     */
    @Select("SELECT * FROM student_group WHERE first_choice = 'PHYSICS'")
    List<StudentGroup> findPhysicsGroups();
    
    /**
     * 查找历史类学生组
     */
    @Select("SELECT * FROM student_group WHERE first_choice = 'HISTORY'")
    List<StudentGroup> findHistoryGroups();
}