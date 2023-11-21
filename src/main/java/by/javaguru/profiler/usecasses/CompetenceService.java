package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.CompetenceRequestDto;
import by.javaguru.profiler.usecasses.dto.CompetenceResponseDto;

public interface CompetenceService {

    CompetenceResponseDto save(CompetenceRequestDto competenceRequestDto, String cvUuid);

    CompetenceResponseDto getCompetenceByCvUuid(String cvUuid);

}
