# 高中排课系统 (High School Timetable Scheduler)

基于 Timefold、Spring Boot 和 MyBatis Plus 的高中课程表排课系统，支持 3+2+1 新高考选科模式。

## 系统特性

### 核心功能
- **3+2+1 选科模式支持**：完整实现新高考选科组合（语数英必修 + 物理/历史首选 + 2门再选）
- **智能排课算法**：基于 Timefold 约束求解器的自动排课
- **合班上课**：支持体育理论、音乐、美术等科目的多班合并上课
- **时间约束**：早读安排在早上，晚自习安排在晚上
- **冲突检测**：自动避免教师、学生、教室的时间冲突

### 技术栈
- **后端框架**：Spring Boot 3.2.1
- **求解引擎**：Timefold Solver 1.6.0
- **数据库ORM**：MyBatis Plus 3.5.5
- **数据库**：H2 (开发) / MySQL (生产)
- **Java版本**：JDK 17+

## 快速开始

### 1. 环境要求
- JDK 17 或更高版本
- Maven 3.6+
- MySQL 8.0+ (生产环境，可选)

### 2. 启动应用

#### 使用 H2 内存数据库（开发模式）
```bash
# 克隆项目
git clone <repository-url>
cd timetable-scheduler

# 编译并启动
mvn spring-boot:run
```

#### 使用 MySQL 数据库（生产模式）
1. 创建数据库：
```sql
CREATE DATABASE timetable_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改 `application.yml` 配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/timetable_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

3. 启动应用：
```bash
mvn spring-boot:run
```

### 3. 访问应用
- 应用访问地址：http://localhost:8080/api
- H2 控制台：http://localhost:8080/api/h2-console (仅开发模式)

## API 文档

### 排课管理接口

#### 生成课程表
```http
POST /api/timetable/generate
```
返回任务ID，用于后续查询状态。

#### 查询排课状态
```http
GET /api/timetable/status/{jobId}
```

#### 获取完整课程表
```http
GET /api/timetable/complete
```

#### 获取学生组课程表
```http
GET /api/timetable/student-group/{groupId}
```

#### 获取教师课程表
```http
GET /api/timetable/teacher/{teacherId}
```

### 选科组合接口

#### 获取所有有效组合
```http
GET /api/timetable/subject-combinations
```

#### 验证选科组合
```http
POST /api/timetable/validate-combination
Content-Type: application/json

["CHINESE", "MATH", "ENGLISH", "PHYSICS", "CHEMISTRY", "BIOLOGY"]
```

#### 获取推荐组合
```http
GET /api/timetable/recommendations/PHYSICS
GET /api/timetable/recommendations/HISTORY
```

### 数据管理接口

#### 教师管理
```http
GET    /api/data/teachers          # 获取所有教师
POST   /api/data/teachers          # 创建教师
PUT    /api/data/teachers/{id}     # 更新教师
DELETE /api/data/teachers/{id}     # 删除教师
```

#### 学生组管理
```http
GET    /api/data/student-groups           # 获取所有学生组
POST   /api/data/student-groups           # 创建学生组
GET    /api/data/student-groups/grade/{grade}  # 按年级查询
```

#### 教室管理
```http
GET    /api/data/classrooms         # 获取所有教室
GET    /api/data/classrooms/large   # 获取大教室
POST   /api/data/classrooms         # 创建教室
```

## 数据模型

### 选科组合模式 (3+2+1)

#### 必修科目 (3门)
- 语文 (CHINESE)
- 数学 (MATH)
- 英语 (ENGLISH)

#### 首选科目 (2选1)
- 物理 (PHYSICS)
- 历史 (HISTORY)

#### 再选科目 (4选2)
- 化学 (CHEMISTRY)
- 生物 (BIOLOGY)
- 政治 (POLITICS)
- 地理 (GEOGRAPHY)

#### 其他科目
- 体育理论 (PE_THEORY) - 支持合班
- 体育实践 (PE_PRACTICE)
- 音乐 (MUSIC) - 支持合班
- 美术 (ART) - 支持合班
- 技术 (TECHNOLOGY) - 支持合班

### 时间安排

#### 日常课程时间
- 早读：07:30-08:00
- 第1-4节：08:00-11:50 (上午)
- 第5-8节：14:00-17:30 (下午)
- 晚自习：19:00-21:35 (3节)

#### 约束规则
- **硬约束**：教师/学生/教室冲突、早读/晚自习时间限制、大教室需求
- **软约束**：教师工作量平衡、学科分布均匀、时间偏好

## 示例数据

### 创建示例教师
```json
{
  "name": "张老师",
  "employeeId": "T001",
  "primarySubject": "MATH",
  "maxDailyLessons": 6,
  "maxWeeklyLessons": 24,
  "isHeadTeacher": true
}
```

### 创建示例学生组
```json
{
  "name": "高一物化生1班",
  "grade": "高一",
  "studentCount": 45,
  "firstChoice": "PHYSICS",
  "secondChoices": "[\"CHEMISTRY\", \"BIOLOGY\"]",
  "description": "物理类传统理科组合"
}
```

### 创建示例教室
```json
{
  "name": "101教室",
  "building": "教学楼A",
  "floor": 1,
  "capacity": 50,
  "isLarge": false,
  "facilities": "多媒体设备"
}
```

## 系统配置

### Timefold 求解器配置
```yaml
timefold:
  solver:
    termination:
      spent-limit: 30s      # 求解时间限制
    environment-mode: REPRODUCIBLE
  solver-manager:
    parallel-solver-count: AUTO
```

### 课时分配配置
- 必修科目：每周6课时
- 首选科目：每周4课时  
- 再选科目：每周4课时
- 体育理论：每周1课时
- 体育实践：每周2课时
- 早读：每周5次
- 晚自习：每周15次

## 部署说明

### Docker 部署
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/timetable-scheduler-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### 生产环境配置
1. 配置 MySQL 数据库连接
2. 调整 JVM 参数：`-Xmx2g -Xms1g`
3. 配置日志级别和输出路径
4. 设置合适的求解时间限制

## 开发指南

### 添加新约束
1. 在 `TimetableConstraintProvider` 中添加约束方法
2. 在 `defineConstraints` 方法中注册新约束
3. 测试约束的硬度（HARD/SOFT）和权重

### 扩展选科模式
1. 修改 `SubjectType` 枚举添加新科目
2. 更新 `SubjectCombinationService` 中的组合逻辑
3. 调整课时分配和约束规则

### 自定义时间安排
1. 修改 `application.yml` 中的时间段配置
2. 更新 `TimetableService.initializeTimeSlots()` 方法
3. 调整约束规则以适应新的时间安排

## 故障排除

### 常见问题
1. **求解时间过长**：减少求解时间限制或简化约束
2. **无法生成可行解**：检查数据完整性和约束冲突
3. **内存不足**：增加JVM堆内存或减少数据规模

### 日志分析
- 查看 Timefold 求解日志：`ai.timefold.solver: DEBUG`
- 检查约束违反情况：查看 score 详情
- 监控求解进度：观察最佳解分数变化

## 贡献指南

1. Fork 本项目
2. 创建功能分支
3. 提交代码变更
4. 发起 Pull Request

## 许可证

MIT License

## 联系方式

如有问题或建议，请通过以下方式联系：
- 提交 Issue
- 发送邮件至：[your-email]

---

*该系统专为高中新高考制度设计，支持复杂的选科组合和排课需求。*