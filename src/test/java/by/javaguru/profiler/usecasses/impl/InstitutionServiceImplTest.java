package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Institution;
import by.javaguru.profiler.persistence.repository.InstitutionRepository;
import by.javaguru.profiler.usecasses.dto.InstitutionRequestDto;
import by.javaguru.profiler.usecasses.dto.InstitutionResponseDto;
import by.javaguru.profiler.usecasses.mapper.InstitutionMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static by.javaguru.profiler.util.InstitutionsTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceImplTest {

    @InjectMocks
    private InstitutionServiceImpl institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private InstitutionMapper institutionMapper;

    @Test
    void shouldReturnNotEmptyInstitutionListWhenGettingInstitutions() {
        Institution institution = createInstitution().build();
        InstitutionResponseDto institutionResponseDto = createInstitutionResponseDto().build();
        int expectedListSize = 1;

        when(institutionMapper.fromEntityToDto(institution)).thenReturn(institutionResponseDto);
        when(institutionRepository.findAllByOrderByName()).thenReturn(List.of(institution));

        List<InstitutionResponseDto> actualResult = institutionService.getInstitutions();
        assertEquals(expectedListSize,actualResult.size());
    }

    @Test
    void shouldReturnInstitutionResponseDtoWhenInstitutionSaved(){
        InstitutionRequestDto requestDto = createInstitutionRequestDto().build();
        Institution institution = createInstitution().build();
        InstitutionResponseDto institutionResponseDto = createInstitutionResponseDto().build();

        when(institutionMapper.fromDtoToEntity(requestDto)).thenReturn(institution);
        when(institutionRepository.save(institution)).thenReturn(institution);
        when(institutionMapper.fromEntityToDto(institution)).thenReturn(institutionResponseDto);

        InstitutionResponseDto savedInstitution = institutionService.save(requestDto);
        assertEquals(requestDto.name(), savedInstitution.name());
    }
}