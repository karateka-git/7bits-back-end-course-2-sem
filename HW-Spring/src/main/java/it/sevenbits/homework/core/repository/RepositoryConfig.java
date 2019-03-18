package it.sevenbits.homework.core.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public TaskRepository taskRepository() {
        return new SimpleTaskRepository();
    }
}
