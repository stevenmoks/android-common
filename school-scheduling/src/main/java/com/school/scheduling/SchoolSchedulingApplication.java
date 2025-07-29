package com.school.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 高中3+2+1分科排课系统启动类
 */
@SpringBootApplication
public class SchoolSchedulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolSchedulingApplication.class, args);
        System.out.println("=================================");
        System.out.println("高中3+2+1分科排课系统启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("H2数据库控制台: http://localhost:8080/h2-console");
        System.out.println("API文档: http://localhost:8080/api/timetable/health");
        System.out.println("=================================");
    }
}