package it.sevenbits.database.config;

import it.sevenbits.database.core.repository.DatabaseTasksRepository;
import it.sevenbits.database.core.repository.TaskRepository;
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
}
