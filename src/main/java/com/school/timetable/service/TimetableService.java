package com.school.timetable.service;

import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.api.solver.SolverStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.timetable.domain.Timetable;
import com.school.timetable.domain.entity.*;
import com.school.timetable.domain.enums.SubjectType;
import com.school.timetable.domain.enums.TimeSlotType;
import com.school.timetable.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 排课服务
 * 实现高中课程表的生成和优化
 */
@Service
public class TimetableService {
    
    @Autowired
    private SolverManager<Timetable, String> solverManager;
    
    @Autowired
    private TimeSlotMapper timeSlotMapper;
    
    @Autowired
    private ClassroomMapper classroomMapper;
    
    @Autowired
    private TeacherMapper teacherMapper;
    
    @Autowired
    private StudentGroupMapper studentGroupMapper;
    
    @Autowired
    private LessonMapper lessonMapper;
    
    @Autowired
    private SubjectCombinationService subjectCombinationService;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * 生成课程表
     */
    public String generateTimetable(String problemId) {
        Timetable unsolvedTimetable = createUnsolvedTimetable();
        
        // 提交求解任务
        solverManager.solveAndListen(problemId, unsolvedTimetable,
            finalBestSolution -> {
                // 保存最终结果
                saveTimetableResult(finalBestSolution);
            });
        
        return problemId;
    }
    
    /**
     * 获取求解状态
     */
    public SolverStatus getSolverStatus(String problemId) {
        return solverManager.getSolverStatus(problemId);
    }
    
    /**
     * 停止求解
     */
    public void terminateSolving(String problemId) {
        solverManager.terminateEarly(problemId);
    }
    
    /**
     * 创建未求解的课程表问题
     */
    private Timetable createUnsolvedTimetable() {
        // 获取基础数据
        List<TimeSlot> timeSlots = initializeTimeSlots();
        List<Classroom> classrooms = classroomMapper.selectList(null);
        List<Teacher> teachers = teacherMapper.selectList(null);
        List<StudentGroup> studentGroups = studentGroupMapper.selectList(null);
        
        // 生成课程
        List<Lesson> lessons = generateLessons(teachers, studentGroups);
        
        return new Timetable(timeSlots, classrooms, teachers, studentGroups, lessons);
    }
    
    /**
     * 初始化时间段
     */
    private List<TimeSlot> initializeTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        
        // 为每个工作日创建时间段
        for (DayOfWeek dayOfWeek : Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, 
                DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            
            // 早读
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(7, 30), LocalTime.of(8, 0), 
                "早读", TimeSlotType.MORNING_READING));
            
            // 第一节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(8, 0), LocalTime.of(8, 45), 
                "第一节", TimeSlotType.REGULAR));
            
            // 第二节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(8, 55), LocalTime.of(9, 40), 
                "第二节", TimeSlotType.REGULAR));
            
            // 第三节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(10, 10), LocalTime.of(10, 55), 
                "第三节", TimeSlotType.REGULAR));
            
            // 第四节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(11, 5), LocalTime.of(11, 50), 
                "第四节", TimeSlotType.REGULAR));
            
            // 第五节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(14, 0), LocalTime.of(14, 45), 
                "第五节", TimeSlotType.REGULAR));
            
            // 第六节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(14, 55), LocalTime.of(15, 40), 
                "第六节", TimeSlotType.REGULAR));
            
            // 第七节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(15, 50), LocalTime.of(16, 35), 
                "第七节", TimeSlotType.REGULAR));
            
            // 第八节
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(16, 45), LocalTime.of(17, 30), 
                "第八节", TimeSlotType.REGULAR));
            
            // 晚自习
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(19, 0), LocalTime.of(19, 45), 
                "晚自习1", TimeSlotType.EVENING_STUDY));
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(19, 55), LocalTime.of(20, 40), 
                "晚自习2", TimeSlotType.EVENING_STUDY));
            timeSlots.add(new TimeSlot(dayOfWeek, LocalTime.of(20, 50), LocalTime.of(21, 35), 
                "晚自习3", TimeSlotType.EVENING_STUDY));
        }
        
        return timeSlots;
    }
    
    /**
     * 生成课程
     */
    private List<Lesson> generateLessons(List<Teacher> teachers, List<StudentGroup> studentGroups) {
        List<Lesson> lessons = new ArrayList<>();
        
        for (StudentGroup group : studentGroups) {
            // 获取该组的选科组合
            List<SubjectType> subjects = getGroupSubjects(group);
            
            for (SubjectType subject : subjects) {
                // 查找合适的教师
                Teacher teacher = findTeacherForSubject(teachers, subject);
                if (teacher == null) continue;
                
                // 获取该科目的周课时数
                Integer weeklyLessons = subjectCombinationService.getDefaultWeeklyLessons(subject);
                
                // 检查是否需要合班
                if (subjectCombinationService.isMergeableSubject(subject)) {
                    // 为合班课程创建课程
                    Lesson mergedLesson = createMergedLesson(subject, teacher, group, weeklyLessons, studentGroups);
                    if (mergedLesson != null) {
                        lessons.add(mergedLesson);
                    }
                } else {
                    // 为普通课程创建多个课程实例（每周课时）
                    for (int i = 0; i < weeklyLessons; i++) {
                        Lesson lesson = new Lesson(subject, teacher.getId(), group.getId(), 1);
                        lesson.setTeacher(teacher);
                        lesson.setStudentGroup(group);
                        lessons.add(lesson);
                    }
                }
            }
        }
        
        return lessons;
    }
    
    /**
     * 获取学生组的选科组合
     */
    private List<SubjectType> getGroupSubjects(StudentGroup group) {
        List<SubjectType> subjects = new ArrayList<>();
        
        // 添加必修科目
        subjects.addAll(Arrays.asList(SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH));
        
        // 添加首选科目
        subjects.add(group.getFirstChoice());
        
        // 添加再选科目
        try {
            if (group.getSecondChoices() != null && !group.getSecondChoices().isEmpty()) {
                List<String> secondChoices = objectMapper.readValue(
                    group.getSecondChoices(), new TypeReference<List<String>>() {});
                for (String choice : secondChoices) {
                    subjects.add(SubjectType.valueOf(choice));
                }
            }
        } catch (JsonProcessingException e) {
            // 如果解析失败，使用默认推荐
            subjects.addAll(subjectCombinationService.getRecommendedSecondChoices(group.getFirstChoice()));
        }
        
        // 添加其他科目
        subjects.addAll(Arrays.asList(
            SubjectType.PE_THEORY, SubjectType.PE_PRACTICE,
            SubjectType.MUSIC, SubjectType.ART, SubjectType.TECHNOLOGY,
            SubjectType.MORNING_READING, SubjectType.EVENING_STUDY
        ));
        
        return subjects;
    }
    
    /**
     * 为科目查找合适的教师
     */
    private Teacher findTeacherForSubject(List<Teacher> teachers, SubjectType subject) {
        return teachers.stream()
            .filter(teacher -> teacher.getPrimarySubject() == subject)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * 创建合班课程
     */
    private Lesson createMergedLesson(SubjectType subject, Teacher teacher, StudentGroup primaryGroup, 
                                     Integer weeklyLessons, List<StudentGroup> allGroups) {
        
        // 查找同年级的其他组进行合班
        List<StudentGroup> mergeableGroups = allGroups.stream()
            .filter(group -> group.getGrade().equals(primaryGroup.getGrade()))
            .filter(group -> !group.getId().equals(primaryGroup.getId()))
            .limit(2) // 最多合并3个班
            .collect(Collectors.toList());
        
        if (mergeableGroups.isEmpty()) {
            // 如果没有可合班的组，创建普通课程
            Lesson lesson = new Lesson(subject, teacher.getId(), primaryGroup.getId(), weeklyLessons);
            lesson.setTeacher(teacher);
            lesson.setStudentGroup(primaryGroup);
            return lesson;
        }
        
        // 创建合班课程
        Lesson mergedLesson = new Lesson(subject, teacher.getId(), primaryGroup.getId(), weeklyLessons);
        mergedLesson.setTeacher(teacher);
        mergedLesson.setStudentGroup(primaryGroup);
        mergedLesson.setRequiresLargeRoom(true);
        
        // 设置合班组合
        List<Long> mergedGroupIds = new ArrayList<>();
        mergedGroupIds.add(primaryGroup.getId());
        mergeableGroups.forEach(group -> mergedGroupIds.add(group.getId()));
        
        try {
            mergedLesson.setMergedGroups(objectMapper.writeValueAsString(mergedGroupIds));
        } catch (JsonProcessingException e) {
            // 如果序列化失败，返回null
            return null;
        }
        
        return mergedLesson;
    }
    
    /**
     * 保存排课结果
     */
    private void saveTimetableResult(Timetable solution) {
        // 删除现有课程
        lessonMapper.delete(null);
        
        // 保存新的课程安排
        for (Lesson lesson : solution.getLessons()) {
            if (lesson.getTimeSlot() != null && lesson.getClassroom() != null) {
                // 保存时间段和教室的ID
                // 注意：这里需要根据实际的ID映射逻辑进行调整
                lessonMapper.insert(lesson);
            }
        }
    }
    
    /**
     * 获取学生组的课程表
     */
    public List<Lesson> getStudentGroupTimetable(Long studentGroupId) {
        List<Lesson> lessons = lessonMapper.findByStudentGroupId(studentGroupId);
        
        // 填充关联对象
        for (Lesson lesson : lessons) {
            if (lesson.getTeacherId() != null) {
                lesson.setTeacher(teacherMapper.selectById(lesson.getTeacherId()));
            }
            if (lesson.getStudentGroupId() != null) {
                lesson.setStudentGroup(studentGroupMapper.selectById(lesson.getStudentGroupId()));
            }
        }
        
        return lessons;
    }
    
    /**
     * 获取教师的课程表
     */
    public List<Lesson> getTeacherTimetable(Long teacherId) {
        List<Lesson> lessons = lessonMapper.findByTeacherId(teacherId);
        
        // 填充关联对象
        for (Lesson lesson : lessons) {
            if (lesson.getStudentGroupId() != null) {
                lesson.setStudentGroup(studentGroupMapper.selectById(lesson.getStudentGroupId()));
            }
        }
        
        return lessons;
    }
    
    /**
     * 获取完整的课程表
     */
    public Timetable getCompleteTimetable() {
        List<TimeSlot> timeSlots = timeSlotMapper.selectList(null);
        List<Classroom> classrooms = classroomMapper.selectList(null);
        List<Teacher> teachers = teacherMapper.selectList(null);
        List<StudentGroup> studentGroups = studentGroupMapper.selectList(null);
        List<Lesson> lessons = lessonMapper.selectList(null);
        
        // 填充关联对象
        for (Lesson lesson : lessons) {
            if (lesson.getTeacherId() != null) {
                lesson.setTeacher(teachers.stream()
                    .filter(t -> t.getId().equals(lesson.getTeacherId()))
                    .findFirst().orElse(null));
            }
            if (lesson.getStudentGroupId() != null) {
                lesson.setStudentGroup(studentGroups.stream()
                    .filter(g -> g.getId().equals(lesson.getStudentGroupId()))
                    .findFirst().orElse(null));
            }
        }
        
        return new Timetable(timeSlots, classrooms, teachers, studentGroups, lessons);
    }
}