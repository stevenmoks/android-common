package com.school.timetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.timetable.domain.entity.Teacher;
import com.school.timetable.domain.enums.SubjectType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 教师Mapper接口
 */
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    
    /**
     * 根据主要科目查找教师
     */
    @Select("SELECT * FROM teacher WHERE primary_subject = #{subject}")
    List<Teacher> findByPrimarySubject(SubjectType subject);
    
    /**
     * 查找班主任
     */
    @Select("SELECT * FROM teacher WHERE is_head_teacher = true")
    List<Teacher> findHeadTeachers();
    
    /**
     * 根据工号查找教师
     */
    @Select("SELECT * FROM teacher WHERE employee_id = #{employeeId}")
    Teacher findByEmployeeId(String employeeId);
}