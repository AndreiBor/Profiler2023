package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.InstitutionRequestDto;
import by.javaguru.profiler.usecasses.dto.InstitutionResponseDto;

import java.util.List;

public interface InstitutionService {
    List<InstitutionResponseDto> getInstitutions();
    boolean isInstitutionExist(Long id);
    InstitutionResponseDto save(InstitutionRequestDto dto);
}
