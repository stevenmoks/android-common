package com.school.scheduling.entity;

/**
 * 学科实体类
 * 3+2+1分科模式：
 * - 3门必选：语文、数学、英语
 * - 2门主选：物理/历史 二选一
 * - 1门副选：政治、地理、化学、生物 四选一
 */
public class Subject {
    
    private Long id;
    
    /**
     * 学科名称
     */
    private String name;
    
    /**
     * 学科代码
     */
    private String code;
    
    /**
     * 学科类型：REQUIRED(必选)、PRIMARY(主选)、SECONDARY(副选)
     */
    private SubjectType type;
    
    /**
     * 每周课时数
     */
    private Integer weeklyHours;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    // 枚举定义
    public enum SubjectType {
        REQUIRED("必选科目"),
        PRIMARY("主选科目"), 
        SECONDARY("副选科目");
        
        private final String description;
        
        SubjectType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // 构造函数
    public Subject() {}
    
    public Subject(String name, String code, SubjectType type, Integer weeklyHours) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.weeklyHours = weeklyHours;
        this.enabled = true;
    }
    
    // Getter and Setter
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public SubjectType getType() {
        return type;
    }
    
    public void setType(SubjectType type) {
        this.type = type;
    }
    
    public Integer getWeeklyHours() {
        return weeklyHours;
    }
    
    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", weeklyHours=" + weeklyHours +
                ", enabled=" + enabled +
                '}';
    }
}