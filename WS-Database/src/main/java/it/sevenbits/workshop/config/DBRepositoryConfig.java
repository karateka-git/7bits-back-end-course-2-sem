package it.sevenbits.workshop.config;

import it.sevenbits.workshop.core.repository.DatabaseTasksRepository;
import it.sevenbits.workshop.core.repository.TaskRepository;
import it.sevenbits.workshop.web.service.ServiceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * config database repository
 */
@Configuration
public class DBRepositoryConfig {
    /**
     *
     * @param jdbcOperations - jdbcOperation
     * @return - task repository
     */
    @Bean
    public TaskRepository tasksRepository(
            final @Qualifier("tasksJdbcOperations") JdbcOperations jdbcOperations) {
        return new DatabaseTasksRepository(jdbcOperations);
    }

    /**
     *
     * @param taskRepository - task repository
     * @return - service repository
     */
    @Bean
    public ServiceRepository serviceRepository(final TaskRepository taskRepository) {
        return new ServiceRepository(taskRepository);
    }

}
