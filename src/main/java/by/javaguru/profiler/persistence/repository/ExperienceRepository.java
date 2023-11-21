package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
