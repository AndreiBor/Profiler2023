package by.javaguru.profiler.usecasses.mapper;

import static by.javaguru.profiler.util.CountryTestData.createCountry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import by.javaguru.profiler.persistence.model.Country;
import by.javaguru.profiler.usecasses.dto.CountryDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CountryMapperTest {

    private final CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldsWhenInvokeFromEntityToDto() {
        List<Country> countries = List.of(createCountry().withId(1L).withCountryName("Afghanistan").build(),
                createCountry().withId(2L).withCountryName("Angola").build());
        List<CountryDto> countryDtos = countryMapper.toDto(countries);

        List<Long> countryIds = countries.stream().map(Country::getId).toList();
        List<Long> countryDtoIds = countryDtos.stream().map(CountryDto::id).toList();
        List<String> countryNames = countries.stream().map(Country::getCountryName).toList();
        List<String> countryDtoNames = countryDtos.stream().map(CountryDto::countryName).toList();

        assertEquals(countryIds, countryDtoIds);
        assertEquals(countryNames, countryDtoNames);
    }
}
