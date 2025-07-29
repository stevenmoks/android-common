package com.school.scheduling.config;

import com.school.scheduling.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * 数据初始化器 - 初始化3+2+1分科排课的基础数据
 */
@Component
public class DataInitializer implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=================================");
        System.out.println("高中3+2+1分科排课系统启动成功！");
        System.out.println("3门必选：语文、数学、英语");
        System.out.println("2门主选：物理/历史 二选一");
        System.out.println("1门副选：政治、地理、化学、生物 四选一");
        System.out.println("=================================");
    }

    /**
     * 初始化学科数据（3+2+1分科模式）
     */
    private void initializeSubjects() {
        // 3门必选科目
        List<Subject> requiredSubjects = Arrays.asList(
                new Subject("语文", "CHI", Subject.SubjectType.REQUIRED, 5),
                new Subject("数学", "MATH", Subject.SubjectType.REQUIRED, 5),
                new Subject("英语", "ENG", Subject.SubjectType.REQUIRED, 4)
        );

        // 2门主选科目（物理/历史 二选一）
        List<Subject> primarySubjects = Arrays.asList(
                new Subject("物理", "PHY", Subject.SubjectType.PRIMARY, 4),
                new Subject("历史", "HIS", Subject.SubjectType.PRIMARY, 4)
        );

        // 4门副选科目（政治、地理、化学、生物 四选一）
        List<Subject> secondarySubjects = Arrays.asList(
                new Subject("政治", "POL", Subject.SubjectType.SECONDARY, 3),
                new Subject("地理", "GEO", Subject.SubjectType.SECONDARY, 3),
                new Subject("化学", "CHE", Subject.SubjectType.SECONDARY, 3),
                new Subject("生物", "BIO", Subject.SubjectType.SECONDARY, 3)
        );

        // 批量插入
        // requiredSubjects.forEach(subjectMapper::insert);
        // primarySubjects.forEach(subjectMapper::insert);
        // secondarySubjects.forEach(subjectMapper::insert);
    }

    /**
     * 初始化时间段数据
     */
    private void initializeTimeSlots() {
        // 上午时间段
        List<LocalTime[]> morningSlots = Arrays.asList(
                new LocalTime[]{LocalTime.of(8, 0), LocalTime.of(8, 45)},   // 第1节
                new LocalTime[]{LocalTime.of(8, 55), LocalTime.of(9, 40)},  // 第2节
                new LocalTime[]{LocalTime.of(10, 10), LocalTime.of(10, 55)}, // 第3节
                new LocalTime[]{LocalTime.of(11, 5), LocalTime.of(11, 50)}   // 第4节
        );

        // 下午时间段
        List<LocalTime[]> afternoonSlots = Arrays.asList(
                new LocalTime[]{LocalTime.of(14, 30), LocalTime.of(15, 15)}, // 第5节
                new LocalTime[]{LocalTime.of(15, 25), LocalTime.of(16, 10)}, // 第6节
                new LocalTime[]{LocalTime.of(16, 20), LocalTime.of(17, 5)}   // 第7节
        );

        // 为每一天创建时间段
        for (DayOfWeek day = DayOfWeek.MONDAY; day.getValue() <= DayOfWeek.FRIDAY.getValue(); day = day.plus(1)) {
            // 上午时间段
            for (int i = 0; i < morningSlots.size(); i++) {
                LocalTime[] slot = morningSlots.get(i);
                TimeSlot timeSlot = new TimeSlot(day, slot[0], slot[1], "第" + (i + 1) + "节");
                // timeSlotMapper.insert(timeSlot);
            }

            // 下午时间段
            for (int i = 0; i < afternoonSlots.size(); i++) {
                LocalTime[] slot = afternoonSlots.get(i);
                TimeSlot timeSlot = new TimeSlot(day, slot[0], slot[1], "第" + (i + 5) + "节");
                // timeSlotMapper.insert(timeSlot);
            }
        }
    }

    /**
     * 初始化教师数据
     */
    private void initializeTeachers() {
        List<Teacher> teachers = Arrays.asList(
                // 语文教师
                new Teacher("张老师", "T001", "[1]", 20),
                new Teacher("李老师", "T002", "[1]", 20),
                
                // 数学教师
                new Teacher("王老师", "T003", "[2]", 20),
                new Teacher("赵老师", "T004", "[2]", 20),
                
                // 英语教师
                new Teacher("陈老师", "T005", "[3]", 18),
                new Teacher("刘老师", "T006", "[3]", 18),
                
                // 物理教师
                new Teacher("周老师", "T007", "[4]", 16),
                new Teacher("吴老师", "T008", "[4]", 16),
                
                // 历史教师
                new Teacher("孙老师", "T009", "[5]", 16),
                new Teacher("郑老师", "T010", "[5]", 16),
                
                // 政治教师
                new Teacher("钱老师", "T011", "[6]", 12),
                
                // 地理教师
                new Teacher("冯老师", "T012", "[7]", 12),
                
                // 化学教师
                new Teacher("蒋老师", "T013", "[8]", 12),
                
                // 生物教师
                new Teacher("韩老师", "T014", "[9]", 12)
        );

        // teachers.forEach(teacherMapper::insert);
    }

    /**
     * 初始化班级数据（3+2+1选科组合）
     */
    private void initializeStudentClasses() {
        List<StudentClass> classes = Arrays.asList(
                // 物理+政治组合班级
                new StudentClass("高二1班", "高二", 45, 4L, 6L),
                new StudentClass("高二2班", "高二", 43, 4L, 6L),
                
                // 物理+地理组合班级
                new StudentClass("高二3班", "高二", 44, 4L, 7L),
                new StudentClass("高二4班", "高二", 46, 4L, 7L),
                
                // 物理+化学组合班级
                new StudentClass("高二5班", "高二", 42, 4L, 8L),
                new StudentClass("高二6班", "高二", 45, 4L, 8L),
                
                // 物理+生物组合班级
                new StudentClass("高二7班", "高二", 44, 4L, 9L),
                
                // 历史+政治组合班级
                new StudentClass("高二8班", "高二", 43, 5L, 6L),
                new StudentClass("高二9班", "高二", 45, 5L, 6L),
                
                // 历史+地理组合班级
                new StudentClass("高二10班", "高二", 44, 5L, 7L),
                
                // 历史+化学组合班级
                new StudentClass("高二11班", "高二", 42, 5L, 8L),
                
                // 历史+生物组合班级
                new StudentClass("高二12班", "高二", 43, 5L, 9L)
        );

        // classes.forEach(studentClassMapper::insert);
    }
}