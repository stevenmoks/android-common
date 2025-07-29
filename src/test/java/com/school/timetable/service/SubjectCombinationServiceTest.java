package com.school.timetable.service;

import com.school.timetable.domain.enums.SubjectType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubjectCombinationServiceTest {
    
    @Autowired
    private SubjectCombinationService subjectCombinationService;
    
    @Test
    public void testGetAllValidCombinations() {
        List<List<SubjectType>> combinations = subjectCombinationService.getAllValidCombinations();
        
        // 应该有12种有效组合 (2个首选科目 * 6种再选组合)
        assertEquals(12, combinations.size());
        
        // 每个组合都应该有6门课
        for (List<SubjectType> combination : combinations) {
            assertEquals(6, combination.size());
        }
    }
    
    @Test
    public void testValidCombination() {
        // 测试有效的物理类组合
        List<SubjectType> validPhysicsCombination = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.CHEMISTRY, SubjectType.BIOLOGY
        );
        assertTrue(subjectCombinationService.isValidCombination(validPhysicsCombination));
        
        // 测试有效的历史类组合
        List<SubjectType> validHistoryCombination = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.HISTORY, SubjectType.POLITICS, SubjectType.GEOGRAPHY
        );
        assertTrue(subjectCombinationService.isValidCombination(validHistoryCombination));
    }
    
    @Test
    public void testInvalidCombination() {
        // 测试无效组合：缺少必修科目
        List<SubjectType> invalidCombination1 = Arrays.asList(
            SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.CHEMISTRY, SubjectType.BIOLOGY
        );
        assertFalse(subjectCombinationService.isValidCombination(invalidCombination1));
        
        // 测试无效组合：两个首选科目
        List<SubjectType> invalidCombination2 = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.HISTORY, SubjectType.CHEMISTRY
        );
        assertFalse(subjectCombinationService.isValidCombination(invalidCombination2));
        
        // 测试无效组合：再选科目数量不对
        List<SubjectType> invalidCombination3 = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.CHEMISTRY
        );
        assertFalse(subjectCombinationService.isValidCombination(invalidCombination3));
    }
    
    @Test
    public void testPhysicsBasedCombinations() {
        List<List<SubjectType>> physicsCombinations = subjectCombinationService.getPhysicsBasedCombinations();
        
        // 应该有6种物理类组合
        assertEquals(6, physicsCombinations.size());
        
        // 所有组合都应该包含物理
        for (List<SubjectType> combination : physicsCombinations) {
            assertTrue(combination.contains(SubjectType.PHYSICS));
            assertFalse(combination.contains(SubjectType.HISTORY));
        }
    }
    
    @Test
    public void testHistoryBasedCombinations() {
        List<List<SubjectType>> historyCombinations = subjectCombinationService.getHistoryBasedCombinations();
        
        // 应该有6种历史类组合
        assertEquals(6, historyCombinations.size());
        
        // 所有组合都应该包含历史
        for (List<SubjectType> combination : historyCombinations) {
            assertTrue(combination.contains(SubjectType.HISTORY));
            assertFalse(combination.contains(SubjectType.PHYSICS));
        }
    }
    
    @Test
    public void testRecommendedWeeklyLessons() {
        List<SubjectType> combination = Arrays.asList(
            SubjectType.CHINESE, SubjectType.MATH, SubjectType.ENGLISH,
            SubjectType.PHYSICS, SubjectType.CHEMISTRY, SubjectType.BIOLOGY
        );
        
        Map<SubjectType, Integer> weeklyLessons = subjectCombinationService.getRecommendedWeeklyLessons(combination);
        
        // 检查必修科目课时
        assertEquals(6, weeklyLessons.get(SubjectType.CHINESE));
        assertEquals(6, weeklyLessons.get(SubjectType.MATH));
        assertEquals(6, weeklyLessons.get(SubjectType.ENGLISH));
        
        // 检查首选科目课时
        assertEquals(4, weeklyLessons.get(SubjectType.PHYSICS));
        
        // 检查再选科目课时
        assertEquals(4, weeklyLessons.get(SubjectType.CHEMISTRY));
        assertEquals(4, weeklyLessons.get(SubjectType.BIOLOGY));
    }
    
    @Test
    public void testMergeableSubjects() {
        var mergeableSubjects = subjectCombinationService.getMergeableSubjects();
        
        // 检查可合班的科目
        assertTrue(mergeableSubjects.contains(SubjectType.PE_THEORY));
        assertTrue(mergeableSubjects.contains(SubjectType.MUSIC));
        assertTrue(mergeableSubjects.contains(SubjectType.ART));
        assertTrue(mergeableSubjects.contains(SubjectType.TECHNOLOGY));
        
        // 检查不可合班的科目
        assertFalse(subjectCombinationService.isMergeableSubject(SubjectType.MATH));
        assertFalse(subjectCombinationService.isMergeableSubject(SubjectType.PHYSICS));
    }
    
    @Test
    public void testRecommendedSecondChoices() {
        // 物理类推荐
        List<SubjectType> physicsRecommendations = subjectCombinationService.getRecommendedSecondChoices(SubjectType.PHYSICS);
        assertEquals(2, physicsRecommendations.size());
        assertTrue(physicsRecommendations.contains(SubjectType.CHEMISTRY));
        assertTrue(physicsRecommendations.contains(SubjectType.BIOLOGY));
        
        // 历史类推荐
        List<SubjectType> historyRecommendations = subjectCombinationService.getRecommendedSecondChoices(SubjectType.HISTORY);
        assertEquals(2, historyRecommendations.size());
        assertTrue(historyRecommendations.contains(SubjectType.POLITICS));
        assertTrue(historyRecommendations.contains(SubjectType.GEOGRAPHY));
    }
}