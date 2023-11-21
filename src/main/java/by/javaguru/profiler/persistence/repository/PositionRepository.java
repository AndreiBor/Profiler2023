package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PositionRepository extends JpaRepository<Position, Long> {
}
