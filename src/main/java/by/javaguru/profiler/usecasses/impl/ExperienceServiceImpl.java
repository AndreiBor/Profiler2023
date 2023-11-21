package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Experience;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.ExperienceService;
import by.javaguru.profiler.usecasses.dto.ExperienceRequestDto;
import by.javaguru.profiler.usecasses.dto.ExperienceResponseDto;
import by.javaguru.profiler.usecasses.mapper.ExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final CurriculumVitaeService curriculumVitaeService;
    private final ExperienceMapper experienceMapper;

    @Override
    @Transactional
    public List<ExperienceResponseDto> save(List<ExperienceRequestDto> listOfExperience, String cvUuid) {
        List<Experience> experience = listOfExperience.stream()
                .map(experienceMapper::fromDtoToEntity)
                .toList();
        List<Experience> experienceList = curriculumVitaeService.saveExperienceToCv(cvUuid, experience);
        return experienceList.stream()
                .map(experienceMapper::fromEntityToDto)
                .toList();
    }

    @Override
    public List<ExperienceResponseDto> getExperienceByCvUuid(String cvUuid) {
        List<Experience> experience = curriculumVitaeService.getCvExperienceByCvUuid(cvUuid);

        return experience.stream()
                .map(experienceMapper::fromEntityToDto)
                .toList();
    }
}
