package by.itacademy.profiler.usecasses;

import by.itacademy.profiler.usecasses.dto.InstitutionRequestDto;
import by.itacademy.profiler.usecasses.dto.InstitutionResponseDto;

import java.util.List;

public interface InstitutionService {
    List<InstitutionResponseDto> getInstitutions();
    boolean isInstitutionExist(Long id);
    InstitutionResponseDto save(InstitutionRequestDto dto);
}
