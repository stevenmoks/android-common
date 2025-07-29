package com.school.timetable.service;

import com.school.timetable.domain.enums.SubjectType;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 选科组合服务
 * 实现高中3+2+1选科组合逻辑
 */
@Service
public class SubjectCombinationService {
    
    // 必修科目 (3门)
    private static final Set<SubjectType> REQUIRED_SUBJECTS = Set.of(
        SubjectType.CHINESE,
        SubjectType.MATH,
        SubjectType.ENGLISH
    );
    
    // 首选科目 (2选1)
    private static final Set<SubjectType> FIRST_CHOICE_SUBJECTS = Set.of(
        SubjectType.PHYSICS,
        SubjectType.HISTORY
    );
    
    // 再选科目 (4选2)
    private static final Set<SubjectType> SECOND_CHOICE_SUBJECTS = Set.of(
        SubjectType.CHEMISTRY,
        SubjectType.BIOLOGY,
        SubjectType.POLITICS,
        SubjectType.GEOGRAPHY
    );
    
    /**
     * 获取所有有效的选科组合
     */
    public List<List<SubjectType>> getAllValidCombinations() {
        List<List<SubjectType>> validCombinations = new ArrayList<>();
        
        // 遍历首选科目
        for (SubjectType firstChoice : FIRST_CHOICE_SUBJECTS) {
            // 获取再选科目的所有2选2组合
            List<List<SubjectType>> secondChoiceCombinations = generateSecondChoiceCombinations();
            
            for (List<SubjectType> secondChoice : secondChoiceCombinations) {
                List<SubjectType> combination = new ArrayList<>();
                combination.addAll(REQUIRED_SUBJECTS);  // 添加必修科目
                combination.add(firstChoice);           // 添加首选科目
                combination.addAll(secondChoice);       // 添加再选科目
                
                validCombinations.add(combination);
            }
        }
        
        return validCombinations;
    }
    
    /**
     * 生成再选科目的所有2选2组合
     */
    private List<List<SubjectType>> generateSecondChoiceCombinations() {
        List<List<SubjectType>> combinations = new ArrayList<>();
        List<SubjectType> subjects = new ArrayList<>(SECOND_CHOICE_SUBJECTS);
        
        // 生成所有2选2的组合
        for (int i = 0; i < subjects.size(); i++) {
            for (int j = i + 1; j < subjects.size(); j++) {
                List<SubjectType> combination = Arrays.asList(subjects.get(i), subjects.get(j));
                combinations.add(combination);
            }
        }
        
        return combinations;
    }
    
    /**
     * 获取物理类组合
     */
    public List<List<SubjectType>> getPhysicsBasedCombinations() {
        return getAllValidCombinations().stream()
            .filter(combination -> combination.contains(SubjectType.PHYSICS))
            .toList();
    }
    
    /**
     * 获取历史类组合
     */
    public List<List<SubjectType>> getHistoryBasedCombinations() {
        return getAllValidCombinations().stream()
            .filter(combination -> combination.contains(SubjectType.HISTORY))
            .toList();
    }
    
    /**
     * 验证选科组合是否有效
     */
    public boolean isValidCombination(List<SubjectType> combination) {
        if (combination == null || combination.size() != 6) {
            return false;
        }
        
        // 检查必修科目
        if (!combination.containsAll(REQUIRED_SUBJECTS)) {
            return false;
        }
        
        // 检查首选科目（必须有且只有一个）
        long firstChoiceCount = combination.stream()
            .filter(FIRST_CHOICE_SUBJECTS::contains)
            .count();
        if (firstChoiceCount != 1) {
            return false;
        }
        
        // 检查再选科目（必须有且只有两个）
        long secondChoiceCount = combination.stream()
            .filter(SECOND_CHOICE_SUBJECTS::contains)
            .count();
        
        return secondChoiceCount == 2;
    }
    
    /**
     * 根据选科组合获取推荐的课时分配
     */
    public Map<SubjectType, Integer> getRecommendedWeeklyLessons(List<SubjectType> combination) {
        Map<SubjectType, Integer> weeklyLessons = new HashMap<>();
        
        for (SubjectType subject : combination) {
            weeklyLessons.put(subject, getDefaultWeeklyLessons(subject));
        }
        
        return weeklyLessons;
    }
    
    /**
     * 获取科目的默认周课时数
     */
    public Integer getDefaultWeeklyLessons(SubjectType subject) {
        switch (subject) {
            case CHINESE:
            case MATH:
            case ENGLISH:
                return 6;  // 必修科目每周6课时
            case PHYSICS:
            case HISTORY:
                return 4;  // 首选科目每周4课时
            case CHEMISTRY:
            case BIOLOGY:
            case POLITICS:
            case GEOGRAPHY:
                return 4;  // 再选科目每周4课时
            case PE_THEORY:
                return 1;  // 体育理论每周1课时
            case PE_PRACTICE:
                return 2;  // 体育实践每周2课时
            case MUSIC:
            case ART:
                return 1;  // 艺术类每周1课时
            case TECHNOLOGY:
                return 1;  // 技术类每周1课时
            case MORNING_READING:
                return 5;  // 早读每周5次（每天一次）
            case EVENING_STUDY:
                return 15; // 晚自习每周15次（每天3次）
            default:
                return 2;
        }
    }
    
    /**
     * 获取需要合班上课的科目
     */
    public Set<SubjectType> getMergeableSubjects() {
        return Set.of(
            SubjectType.PE_THEORY,     // 体育理论课
            SubjectType.MUSIC,         // 音乐课
            SubjectType.ART,           // 美术课
            SubjectType.TECHNOLOGY     // 技术课
        );
    }
    
    /**
     * 检查科目是否适合合班上课
     */
    public boolean isMergeableSubject(SubjectType subject) {
        return getMergeableSubjects().contains(subject);
    }
    
    /**
     * 获取科目的中文名称
     */
    public String getSubjectChineseName(SubjectType subject) {
        return subject.getName();
    }
    
    /**
     * 根据首选科目获取对应的再选科目推荐
     */
    public List<SubjectType> getRecommendedSecondChoices(SubjectType firstChoice) {
        if (firstChoice == SubjectType.PHYSICS) {
            // 物理类推荐理科组合
            return Arrays.asList(SubjectType.CHEMISTRY, SubjectType.BIOLOGY);
        } else if (firstChoice == SubjectType.HISTORY) {
            // 历史类推荐文科组合
            return Arrays.asList(SubjectType.POLITICS, SubjectType.GEOGRAPHY);
        }
        return new ArrayList<>();
    }
}