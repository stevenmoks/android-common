package com.school.timetable.solver;

import ai.timefold.solver.core.api.score.buildin.hardsoft.HardSoftScore;
import ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.api.score.stream.ConstraintCollectors;
import ai.timefold.solver.core.api.score.stream.ConstraintFactory;
import ai.timefold.solver.core.api.score.stream.ConstraintProvider;
import ai.timefold.solver.core.api.score.stream.Joiners;
import com.school.timetable.domain.entity.Lesson;
import com.school.timetable.domain.enums.SubjectType;
import com.school.timetable.domain.enums.TimeSlotType;

/**
 * 课程表约束提供者
 * 定义高中排课系统的各种约束规则
 */
public class TimetableConstraintProvider implements ConstraintProvider {
    
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[] {
            // 硬约束 (Hard Constraints) - 必须满足的约束
            classroomConflict(constraintFactory),
            teacherConflict(constraintFactory),
            studentGroupConflict(constraintFactory),
            morningReadingTimeSlot(constraintFactory),
            eveningStudyTimeSlot(constraintFactory),
            physicalEducationLargeRoom(constraintFactory),
            requiredSubjectsCoverage(constraintFactory),
            
            // 软约束 (Soft Constraints) - 优化目标
            teacherWorkloadBalance(constraintFactory),
            subjectDistribution(constraintFactory),
            consecutiveLessonsPreference(constraintFactory),
            teacherPreferredTimeSlots(constraintFactory),
            studentGroupPreference(constraintFactory)
        };
    }
    
    /**
     * 硬约束：同一时间段同一教室不能有多个课程
     */
    private Constraint classroomConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEachUniquePair(Lesson.class,
                Joiners.equal(Lesson::getTimeSlot),
                Joiners.equal(Lesson::getClassroom))
            .filter((lesson1, lesson2) -> lesson1.getTimeSlot() != null && lesson1.getClassroom() != null)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("教室冲突");
    }
    
    /**
     * 硬约束：同一时间段同一教师不能教授多个课程
     */
    private Constraint teacherConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEachUniquePair(Lesson.class,
                Joiners.equal(Lesson::getTimeSlot),
                Joiners.equal(Lesson::getTeacherId))
            .filter((lesson1, lesson2) -> lesson1.getTimeSlot() != null)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("教师冲突");
    }
    
    /**
     * 硬约束：同一时间段同一学生组不能有多个课程
     */
    private Constraint studentGroupConflict(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEachUniquePair(Lesson.class,
                Joiners.equal(Lesson::getTimeSlot),
                Joiners.equal(Lesson::getStudentGroupId))
            .filter((lesson1, lesson2) -> lesson1.getTimeSlot() != null)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("学生组冲突");
    }
    
    /**
     * 硬约束：早读课必须安排在早读时间段
     */
    private Constraint morningReadingTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getSubject() == SubjectType.MORNING_READING)
            .filter(lesson -> lesson.getTimeSlot() != null && 
                             lesson.getTimeSlot().getType() != TimeSlotType.MORNING_READING)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("早读时间约束");
    }
    
    /**
     * 硬约束：晚自习必须安排在晚自习时间段
     */
    private Constraint eveningStudyTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getSubject() == SubjectType.EVENING_STUDY)
            .filter(lesson -> lesson.getTimeSlot() != null && 
                             lesson.getTimeSlot().getType() != TimeSlotType.EVENING_STUDY)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("晚自习时间约束");
    }
    
    /**
     * 硬约束：体育理论课需要大教室（合班上课）
     */
    private Constraint physicalEducationLargeRoom(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getSubject() == SubjectType.PE_THEORY)
            .filter(lesson -> lesson.getClassroom() != null && 
                             !lesson.getClassroom().getIsLarge())
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("体育理论课教室约束");
    }
    
    /**
     * 硬约束：必修科目覆盖度检查
     */
    private Constraint requiredSubjectsCoverage(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getSubject() != null && lesson.getSubject().isRequired())
            .filter(lesson -> lesson.getTimeSlot() == null || lesson.getClassroom() == null)
            .penalize(HardSoftScore.ONE_HARD)
            .asConstraint("必修科目覆盖");
    }
    
    /**
     * 软约束：教师工作量平衡
     */
    private Constraint teacherWorkloadBalance(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getTimeSlot() != null)
            .groupBy(Lesson::getTeacherId, ConstraintCollectors.count())
            .filter((teacherId, lessonCount) -> lessonCount > 6) // 每天超过6节课的惩罚
            .penalize(HardSoftScore.ONE_SOFT, (teacherId, lessonCount) -> lessonCount - 6)
            .asConstraint("教师工作量平衡");
    }
    
    /**
     * 软约束：学科分布均匀（避免同一科目连续过多课时）
     */
    private Constraint subjectDistribution(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEachUniquePair(Lesson.class,
                Joiners.equal(lesson -> lesson.getTimeSlot() != null ? lesson.getTimeSlot().getDayOfWeek() : null),
                Joiners.equal(Lesson::getStudentGroupId),
                Joiners.equal(Lesson::getSubject))
            .filter((lesson1, lesson2) -> lesson1.getTimeSlot() != null && 
                                         lesson2.getTimeSlot() != null)
            .filter((lesson1, lesson2) -> isConsecutiveTimeSlots(lesson1, lesson2))
            .penalize(HardSoftScore.ONE_SOFT)
            .asConstraint("学科分布均匀");
    }
    
    /**
     * 软约束：同一科目的连续课程偏好（某些科目适合连上）
     */
    private Constraint consecutiveLessonsPreference(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEachUniquePair(Lesson.class,
                Joiners.equal(Lesson::getStudentGroupId),
                Joiners.equal(Lesson::getSubject))
            .filter((lesson1, lesson2) -> lesson1.getTimeSlot() != null && 
                                         lesson2.getTimeSlot() != null)
            .filter((lesson1, lesson2) -> isConsecutiveTimeSlots(lesson1, lesson2))
            .filter((lesson1, lesson2) -> preferConsecutive(lesson1.getSubject()))
            .reward(HardSoftScore.ONE_SOFT)
            .asConstraint("连续课程偏好");
    }
    
    /**
     * 软约束：教师偏好时间段
     */
    private Constraint teacherPreferredTimeSlots(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getTimeSlot() != null)
            .filter(lesson -> isUnpreferredTimeSlot(lesson))
            .penalize(HardSoftScore.ONE_SOFT)
            .asConstraint("教师时间偏好");
    }
    
    /**
     * 软约束：学生组偏好（某些科目在特定时间效果更好）
     */
    private Constraint studentGroupPreference(ConstraintFactory constraintFactory) {
        return constraintFactory
            .forEach(Lesson.class)
            .filter(lesson -> lesson.getTimeSlot() != null)
            .filter(lesson -> isOptimalTimeForSubject(lesson))
            .reward(HardSoftScore.ONE_SOFT)
            .asConstraint("学生组时间偏好");
    }
    
    // 辅助方法
    
    private boolean isConsecutiveTimeSlots(Lesson lesson1, Lesson lesson2) {
        if (lesson1.getTimeSlot() == null || lesson2.getTimeSlot() == null) {
            return false;
        }
        // 简化实现：检查时间是否相邻
        return Math.abs(lesson1.getTimeSlot().getStartTime().getHour() - 
                       lesson2.getTimeSlot().getStartTime().getHour()) == 1;
    }
    
    private boolean preferConsecutive(SubjectType subject) {
        // 某些科目适合连续上课
        return subject == SubjectType.MATH || 
               subject == SubjectType.PHYSICS || 
               subject == SubjectType.CHEMISTRY;
    }
    
    private boolean isUnpreferredTimeSlot(Lesson lesson) {
        // 简化实现：下午最后一节课不太受欢迎
        return lesson.getTimeSlot().getStartTime().getHour() >= 16;
    }
    
    private boolean isOptimalTimeForSubject(Lesson lesson) {
        SubjectType subject = lesson.getSubject();
        int hour = lesson.getTimeSlot().getStartTime().getHour();
        
        // 上午时间适合理科
        if (hour >= 8 && hour <= 11) {
            return subject == SubjectType.MATH || 
                   subject == SubjectType.PHYSICS || 
                   subject == SubjectType.CHEMISTRY;
        }
        
        // 下午时间适合文科
        if (hour >= 14 && hour <= 16) {
            return subject == SubjectType.CHINESE || 
                   subject == SubjectType.ENGLISH || 
                   subject == SubjectType.HISTORY;
        }
        
        return false;
    }
}