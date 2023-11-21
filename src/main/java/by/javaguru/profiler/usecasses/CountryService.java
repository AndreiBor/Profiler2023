package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.CountryDto;

import java.util.List;

public interface CountryService {
    List<CountryDto> getCountries();
    boolean isCountryExist(Long id);
}
