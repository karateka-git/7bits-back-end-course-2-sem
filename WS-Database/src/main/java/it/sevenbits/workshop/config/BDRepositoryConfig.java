package it.sevenbits.workshop.config;

import it.sevenbits.workshop.core.repository.DatabaseTasksRepository;
import it.sevenbits.workshop.core.repository.TaskRepository;
import it.sevenbits.workshop.web.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
public class BDRepositoryConfig {
    @Bean
    public TaskRepository tasksRepository(
            @Qualifier("tasksJdbcOperations") JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }

    @Bean
    public ServiceRepository serviceRepository(TaskRepository taskRepository) {
        return new ServiceRepository(taskRepository);
    }

}
