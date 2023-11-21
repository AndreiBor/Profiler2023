package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    List<Institution> findAllByOrderByName();
}
