package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.persistence.repository.CountryRepository;
import by.javaguru.profiler.usecasses.CountryService;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import by.javaguru.profiler.usecasses.mapper.CountryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    public static final String COUNTRY_NAME = "countryName";
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    public List<CountryDto> getCountries() {
        List<Country> countries = countryRepository.findAll(Sort.by(Sort.Order.asc(COUNTRY_NAME)));
        log.debug("Getting {} countries from Database", countries.size());
        return countryMapper.toDto(countries);
    }

    @Override
    public boolean isCountryExist(Long id) {
        return countryRepository.existsById(id);
    }
}
