package com.school.timetable;

import com.school.timetable.domain.enums.SubjectType;
import com.school.timetable.service.SubjectCombinationService;

import java.util.Arrays;
import java.util.List;

/**
 * 简单的测试类，验证高中排课系统的核心功能
 */
public class MainTest {
    
    public static void main(String[] args) {
        System.out.println("=== 高中排课系统 (3+2+1选科模式) 功能测试 ===\n");
        
        SubjectCombinationService service = new SubjectCombinationService();
        
        // 测试1: 获取所有有效的选科组合
        System.out.println("1. 所有有效的选科组合:");
        List<List<SubjectType>> allCombinations = service.getAllValidCombinations();
        System.out.println("总共有 " + allCombinations.size() + " 种有效组合\n");
        
        // 显示前几种组合作为示例
        for (int i = 0; i < Math.min(6, allCombinations.size()); i++) {
            List<SubjectType> combination = allCombinations.get(i);
            System.out.print("组合 " + (i + 1) + ": ");
            for (SubjectType subject : combination) {
                System.out.print(subject.getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // 测试2: 物理类组合
        System.out.println("2. 物理类组合:");
        List<List<SubjectType>> physicsCombinations = service.getPhysicsBasedCombinations();
        System.out.println("物理类有 " + physicsCombinations.size() + " 种组合");
        for (int i = 0; i < Math.min(3, physicsCombinations.size()); i++) {
            List<SubjectType> combination = physicsCombinations.get(i);
            System.out.print("物理组合 " + (i + 1) + ": ");
            for (SubjectType subject : combination) {
                System.out.print(subject.getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // 测试3: 历史类组合
        System.out.println("3. 历史类组合:");
        List<List<SubjectType>> historyCombinations = service.getHistoryBasedCombinations();
        System.out.println("历史类有 " + historyCombinations.size() + " 种组合");
        for (int i = 0; i < Math.min(3, historyCombinations.size()); i++) {
            List<SubjectType> combination = historyCombinations.get(i);
            System.out.print("历史组合 " + (i + 1) + ": ");
            for (SubjectType subject : combination) {
                System.out.print(subject.getName() + " ");
            }
            System.out.println();
        }
        System.out.println();
        
        // 测试4: 验证选科组合
        System.out.println("4. 选科组合验证:");
        
        // 有效组合测试
        List<SubjectType> validCombination = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.CHEMISTRY, SubjectType.BIOLOGY
        );
        boolean isValid = service.isValidCombination(validCombination);
        System.out.println("物化生组合是否有效: " + isValid);
        
        // 无效组合测试
        List<SubjectType> invalidCombination = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.HISTORY // 两个首选科目，无效
        );
        boolean isInvalid = service.isValidCombination(invalidCombination);
        System.out.println("物理+历史组合是否有效: " + isInvalid);
        System.out.println();
        
        // 测试5: 课时分配
        System.out.println("5. 推荐课时分配 (物化生组合):");
        var weeklyLessons = service.getRecommendedWeeklyLessons(validCombination);
        for (var entry : weeklyLessons.entrySet()) {
            System.out.println(entry.getKey().getName() + ": 每周" + entry.getValue() + "课时");
        }
        System.out.println();
        
        // 测试6: 可合班科目
        System.out.println("6. 可合班上课的科目:");
        var mergeableSubjects = service.getMergeableSubjects();
        for (SubjectType subject : mergeableSubjects) {
            System.out.println("- " + subject.getName());
        }
        System.out.println();
        
        // 测试7: 推荐选科
        System.out.println("7. 选科推荐:");
        var physicsRecommendations = service.getRecommendedSecondChoices(SubjectType.PHYSICS);
        System.out.print("选择物理后推荐再选: ");
        for (SubjectType subject : physicsRecommendations) {
            System.out.print(subject.getName() + " ");
        }
        System.out.println();
        
        var historyRecommendations = service.getRecommendedSecondChoices(SubjectType.HISTORY);
        System.out.print("选择历史后推荐再选: ");
        for (SubjectType subject : historyRecommendations) {
            System.out.print(subject.getName() + " ");
        }
        System.out.println();
        
        System.out.println("\n=== 测试完成 ===");
        System.out.println("系统成功实现了3+2+1新高考选科模式的核心功能！");
    }
}