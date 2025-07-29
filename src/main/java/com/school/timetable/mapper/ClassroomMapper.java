package com.school.timetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.timetable.domain.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 教室Mapper接口
 */
@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {
    
    /**
     * 查找大教室（用于合班上课）
     */
    @Select("SELECT * FROM classroom WHERE is_large = true ORDER BY capacity DESC")
    List<Classroom> findLargeClassrooms();
    
    /**
     * 查找普通教室
     */
    @Select("SELECT * FROM classroom WHERE is_large = false ORDER BY capacity")
    List<Classroom> findRegularClassrooms();
    
    /**
     * 根据容量查找合适的教室
     */
    @Select("SELECT * FROM classroom WHERE capacity >= #{minCapacity} ORDER BY capacity")
    List<Classroom> findByMinCapacity(Integer minCapacity);
}