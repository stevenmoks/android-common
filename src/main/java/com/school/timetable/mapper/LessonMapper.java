package com.school.timetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.timetable.domain.entity.Lesson;
import com.school.timetable.domain.enums.SubjectType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 课程Mapper接口
 */
@Mapper
public interface LessonMapper extends BaseMapper<Lesson> {
    
    /**
     * 根据学生组ID查找课程
     */
    @Select("SELECT * FROM lesson WHERE student_group_id = #{studentGroupId}")
    List<Lesson> findByStudentGroupId(Long studentGroupId);
    
    /**
     * 根据教师ID查找课程
     */
    @Select("SELECT * FROM lesson WHERE teacher_id = #{teacherId}")
    List<Lesson> findByTeacherId(Long teacherId);
    
    /**
     * 根据科目查找课程
     */
    @Select("SELECT * FROM lesson WHERE subject = #{subject}")
    List<Lesson> findBySubject(SubjectType subject);
    
    /**
     * 查找需要大教室的课程
     */
    @Select("SELECT * FROM lesson WHERE requires_large_room = true")
    List<Lesson> findRequiringLargeRoom();
    
    /**
     * 查找合班课程
     */
    @Select("SELECT * FROM lesson WHERE merged_groups IS NOT NULL AND merged_groups != ''")
    List<Lesson> findMergedClasses();
}