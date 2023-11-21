package by.javaguru.profiler.persistence.repository;

import by.javaguru.profiler.persistence.model.PhoneCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneCodeRepository extends JpaRepository<PhoneCode, Long> {
    @Override
    @Query("SELECT p FROM PhoneCode p JOIN Country c ON p.country.id = c.id " +
            "ORDER BY FIELD(c.countryName,'Lithuania','Latvia','Ukraine','Russia','Belarus') DESC")
    List<PhoneCode> findAll();

    @Query("SELECT p FROM PhoneCode p WHERE p.code =:code")
    PhoneCode findByCode(@Param("code") int code);

    PhoneCode getPhoneCodeById(Long id);
}
