package com.school.timetable.config;

import ai.timefold.solver.core.api.solver.SolverFactory;
import ai.timefold.solver.core.api.solver.SolverManager;
import ai.timefold.solver.core.config.solver.SolverConfig;
import ai.timefold.solver.core.config.solver.termination.TerminationConfig;
import com.school.timetable.domain.Timetable;
import com.school.timetable.solver.TimetableConstraintProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Timefold配置类
 */
@Configuration
public class TimetableConfig {
    
    @Bean
    public SolverFactory<Timetable> solverFactory() {
        SolverConfig solverConfig = new SolverConfig()
            .withSolutionClass(Timetable.class)
            .withEntityClasses(com.school.timetable.domain.entity.Lesson.class)
            .withConstraintProviderClass(TimetableConstraintProvider.class)
            .withTerminationConfig(new TerminationConfig()
                .withSpentLimit(Duration.ofSeconds(30)));
        
        return SolverFactory.create(solverConfig);
    }
    
    @Bean
    public SolverManager<Timetable, String> solverManager(SolverFactory<Timetable> solverFactory) {
        return SolverManager.create(solverFactory);
    }
}