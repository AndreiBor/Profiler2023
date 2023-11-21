package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.usecasses.dto.ExperienceRequestDto;
import by.javaguru.profiler.usecasses.dto.ExperienceResponseDto;

import java.util.List;

public interface ExperienceService {
    List<ExperienceResponseDto> save(List<ExperienceRequestDto> listOfExperience, String cvUuid);

    List<ExperienceResponseDto> getExperienceByCvUuid(String cvUuid);

}
