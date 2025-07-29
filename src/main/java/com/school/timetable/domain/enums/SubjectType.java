package com.school.timetable.domain.enums;

/**
 * 学科类型枚举
 */
public enum SubjectType {
    // 必修科目
    CHINESE("语文", true, false),
    MATH("数学", true, false),
    ENGLISH("英语", true, false),
    
    // 选考科目 - 首选科目
    PHYSICS("物理", false, true),
    HISTORY("历史", false, true),
    
    // 选考科目 - 再选科目
    CHEMISTRY("化学", false, false),
    BIOLOGY("生物", false, false),
    POLITICS("政治", false, false),
    GEOGRAPHY("地理", false, false),
    
    // 其他科目
    PE_THEORY("体育理论", false, false),
    PE_PRACTICE("体育实践", false, false),
    MUSIC("音乐", false, false),
    ART("美术", false, false),
    TECHNOLOGY("技术", false, false),
    MORNING_READING("早读", false, false),
    EVENING_STUDY("晚自习", false, false);
    
    private final String name;
    private final boolean isRequired;      // 是否为必修科目
    private final boolean isFirstChoice;   // 是否为首选科目（物理/历史）
    
    SubjectType(String name, boolean isRequired, boolean isFirstChoice) {
        this.name = name;
        this.isRequired = isRequired;
        this.isFirstChoice = isFirstChoice;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isRequired() {
        return isRequired;
    }
    
    public boolean isFirstChoice() {
        return isFirstChoice;
    }
    
    public boolean isElective() {
        return !isRequired && !isFirstChoice;
    }
}