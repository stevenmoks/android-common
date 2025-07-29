# 高中3+2+1分科排课系统

基于Timefold Solver、SpringBoot和MyBatis-Plus构建的高中新高考3+2+1分科排课系统。

## 项目介绍

本系统专门针对高中新高考3+2+1分科模式设计，实现智能排课功能：

- **3门必选科目**: 语文、数学、英语
- **2门主选科目**: 物理/历史 二选一  
- **1门副选科目**: 政治、地理、化学、生物 四选一

系统采用Timefold Solver优化引擎，能够自动处理教师分配、时间冲突、课程分布等复杂约束，生成高质量的排课方案。

## 技术栈

- **后端框架**: Spring Boot 3.2.0
- **优化引擎**: Timefold Solver 1.5.0
- **ORM框架**: MyBatis-Plus 3.5.5
- **数据库**: H2 (开发环境) / MySQL (生产环境)
- **Java版本**: JDK 17

## 项目结构

```
school-scheduling/
├── src/main/java/com/school/scheduling/
│   ├── entity/          # 实体类
│   │   ├── Subject.java      # 学科实体
│   │   ├── Teacher.java      # 教师实体
│   │   ├── StudentClass.java # 班级实体
│   │   ├── TimeSlot.java     # 时间段实体
│   │   ├── Lesson.java       # 课程实体(Timefold规划实体)
│   │   └── TimeTable.java    # 课程表解决方案
│   ├── mapper/          # MyBatis-Plus Mapper
│   ├── service/         # 业务服务层
│   ├── controller/      # REST API控制器
│   ├── solver/          # Timefold约束提供者
│   └── config/          # 配置类
├── src/main/resources/
│   ├── application.yml       # 应用配置
│   └── solverConfig.xml      # Timefold求解器配置
└── pom.xml              # Maven依赖配置
```

## 核心功能

### 1. 3+2+1分科管理
- 支持必选、主选、副选三种学科类型
- 自动生成选科组合班级
- 灵活的课时配置

### 2. 智能排课算法
- **硬约束**: 教师冲突、班级冲突、时间限制
- **软约束**: 工作量平衡、优选时间段、学科分布

### 3. 约束规则
- 同一时间段教师不能上多节课
- 同一时间段班级不能上多节课  
- 教师不可用时间段限制
- 重要科目优先安排在黄金时间段
- 同一学科课程分散在不同天

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+

### 运行步骤

1. **克隆项目**
```bash
git clone <repository-url>
cd school-scheduling
```

2. **编译运行**
```bash
mvn clean package
mvn spring-boot:run
```

3. **访问应用**
- 应用地址: http://localhost:8080
- H2数据库控制台: http://localhost:8080/h2-console
- 健康检查: http://localhost:8080/api/timetable/health

### 数据库配置

开发环境默认使用H2内存数据库，生产环境可配置MySQL：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/school_scheduling
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: your_username
    password: your_password
```

## API接口

### 排课管理
- `POST /api/timetable/solve` - 启动排课求解
- `GET /api/timetable/current` - 获取当前排课结果
- `POST /api/timetable/stop/{problemId}` - 停止排课求解

### 学科管理
- `GET /api/subjects` - 获取所有学科
- `GET /api/subjects/type/{type}` - 根据类型获取学科
- `POST /api/subjects` - 创建学科
- `PUT /api/subjects/{id}` - 更新学科
- `DELETE /api/subjects/{id}` - 删除学科

### 班级管理
- `GET /api/classes` - 获取所有班级
- `GET /api/classes/grade/{grade}` - 根据年级获取班级
- `GET /api/classes/combination` - 根据选科组合获取班级
- `POST /api/classes` - 创建班级
- `PUT /api/classes/{id}` - 更新班级
- `DELETE /api/classes/{id}` - 删除班级

## 示例数据

系统启动时会自动初始化以下示例数据：

### 学科配置
- **必选科目**: 语文(5节/周)、数学(5节/周)、英语(4节/周)
- **主选科目**: 物理(4节/周)、历史(4节/周)
- **副选科目**: 政治(3节/周)、地理(3节/周)、化学(3节/周)、生物(3节/周)

### 班级设置
创建12个班级，涵盖所有可能的3+2+1选科组合：
- 物理+政治、物理+地理、物理+化学、物理+生物
- 历史+政治、历史+地理、历史+化学、历史+生物

### 时间安排
- 周一至周五，每天7节课
- 上午：8:00-11:50 (4节课)
- 下午：14:30-17:05 (3节课)

## 配置说明

### Timefold求解器配置
```xml
<termination>
    <spentLimit>PT30S</spentLimit>  <!-- 30秒求解时间限制 -->
    <bestScoreLimit>0hard/0soft</bestScoreLimit>  <!-- 达到完美分数时停止 -->
</termination>
```

### 约束权重调整
可在`TimeTableConstraintProvider`中调整各种约束的权重：
- 硬约束使用`HardSoftScore.ONE_HARD`
- 软约束使用`HardSoftScore.ONE_SOFT`

## 扩展开发

### 添加新约束
1. 在`TimeTableConstraintProvider`中添加新的约束方法
2. 在`defineConstraints`方法中注册新约束

### 自定义实体
1. 扩展现有实体类添加新字段
2. 更新对应的Mapper和Service
3. 修改数据初始化逻辑

### 优化算法
1. 调整`solverConfig.xml`中的算法配置
2. 修改终止条件和搜索策略
3. 添加自定义移动选择器

## 许可证

本项目采用MIT许可证，详见LICENSE文件。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目！