package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
