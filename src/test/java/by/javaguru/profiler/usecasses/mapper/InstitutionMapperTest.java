package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Institution;
import by.javaguru.profiler.usecasses.dto.InstitutionRequestDto;
import by.javaguru.profiler.usecasses.dto.InstitutionResponseDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.javaguru.profiler.util.InstitutionsTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class InstitutionMapperTest {

    private final InstitutionMapper institutionMapper = Mappers.getMapper(InstitutionMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        Institution institution = createInstitution().build();
        InstitutionResponseDto institutionResponseDto = institutionMapper.fromEntityToDto(institution);

        assertEquals(institution.getId(), institutionResponseDto.id());
        assertEquals(institution.getName(), institutionResponseDto.name());
    }

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromDtoToEntity() {
        InstitutionRequestDto institutionRequestDto = createInstitutionRequestDto().build();
        Institution institution = institutionMapper.fromDtoToEntity(institutionRequestDto);

        assertEquals(institution.getName(), institutionRequestDto.name());
    }
}