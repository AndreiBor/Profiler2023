package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.CvLanguage;
import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.CompetenceService;
import by.javaguru.profiler.usecasses.SkillService;
import by.javaguru.profiler.usecasses.dto.CvLanguageRequestDto;
import by.javaguru.profiler.usecasses.dto.CompetenceRequestDto;
import by.javaguru.profiler.usecasses.dto.CompetenceResponseDto;
import by.javaguru.profiler.usecasses.mapper.CompetenceMapper;
import by.javaguru.profiler.usecasses.mapper.CvLanguageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetenceServiceImpl implements CompetenceService {

    private final CurriculumVitaeService curriculumVitaeService;
    private final SkillService skillService;
    private final CvLanguageMapper cvLanguageMapper;
    private final CompetenceMapper competenceMapper;

    @Override
    @Transactional
    public CompetenceResponseDto save(CompetenceRequestDto competenceRequestDto, String cvUuid) {
        List<Long> skillIds = competenceRequestDto.skills();
        List<Skill> skills = skillService.getListOfSkillsByIds(skillIds);
        curriculumVitaeService.saveSkillsToCv(cvUuid, skills);

        List<CvLanguageRequestDto> languages = competenceRequestDto.languages();
        List<CvLanguage> cvLanguages = languages.stream()
                .map(cvLanguageMapper::fromDtoToEntity)
                .toList();
        curriculumVitaeService.saveLanguagesToCv(cvUuid, cvLanguages);

        return competenceMapper.fromEntitiesToDto(skills, cvLanguages);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetenceResponseDto getCompetenceByCvUuid(String cvUuid) {
        List<CvLanguage> cvLanguages = curriculumVitaeService.getCvLanguagesByCvUuid(cvUuid);
        List<Skill> skills = curriculumVitaeService.getCvSkillsByCvUuid(cvUuid);

        return competenceMapper.fromEntitiesToDto(skills, cvLanguages);
    }
}
