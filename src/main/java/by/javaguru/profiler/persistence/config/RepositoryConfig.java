package by.javaguru.profiler.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("by.javaguru.profiler.persistence.model")
@EnableJpaRepositories("by.javaguru.profiler.persistence.repository")
public class RepositoryConfig {
}
