package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.persistence.repository.CountryRepository;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import by.javaguru.profiler.usecasses.mapper.CountryMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static by.javaguru.profiler.util.CountryTestData.createCountry;
import static by.javaguru.profiler.util.CountryTestData.createCountryDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private CountryRepository countryRepository;

    @Test
    void shoulReturnAllCountriesWhenInvokeGetCountries() {
        List<Country> countries = List.of(createCountry().build());
        List<CountryDto> countryDtos = List.of(createCountryDto().build());

        when(countryRepository.findAll(Sort.by(Sort.Order.asc(CountryServiceImpl.COUNTRY_NAME)))).thenReturn(countries);
        when(countryMapper.toDto(countries)).thenReturn(countryDtos);

        List<CountryDto> countryDtoResponse = countryService.getCountries();
        assertEquals(1, countryDtoResponse.size());
    }
}
