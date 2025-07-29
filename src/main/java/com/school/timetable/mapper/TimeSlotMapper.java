package com.school.timetable.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.school.timetable.domain.entity.TimeSlot;
import com.school.timetable.domain.enums.TimeSlotType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.DayOfWeek;
import java.util.List;

/**
 * 时间段Mapper接口
 */
@Mapper
public interface TimeSlotMapper extends BaseMapper<TimeSlot> {
    
    /**
     * 根据类型查找时间段
     */
    @Select("SELECT * FROM time_slot WHERE type = #{type}")
    List<TimeSlot> findByType(TimeSlotType type);
    
    /**
     * 根据星期几查找时间段
     */
    @Select("SELECT * FROM time_slot WHERE day_of_week = #{dayOfWeek}")
    List<TimeSlot> findByDayOfWeek(DayOfWeek dayOfWeek);
    
    /**
     * 查找可用于常规课程的时间段
     */
    @Select("SELECT * FROM time_slot WHERE type = 'REGULAR' ORDER BY day_of_week, start_time")
    List<TimeSlot> findRegularTimeSlots();
}