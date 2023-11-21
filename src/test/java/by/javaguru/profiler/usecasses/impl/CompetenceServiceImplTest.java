package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.CvLanguage;
import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import by.javaguru.profiler.usecasses.SkillService;
import by.javaguru.profiler.usecasses.dto.CompetenceRequestDto;
import by.javaguru.profiler.usecasses.dto.CompetenceResponseDto;
import by.javaguru.profiler.usecasses.dto.CvLanguageRequestDto;
import by.javaguru.profiler.usecasses.mapper.CompetenceMapper;
import by.javaguru.profiler.usecasses.mapper.CvLanguageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static by.javaguru.profiler.util.CompetenceTestData.createCompetenceRequestDto;
import static by.javaguru.profiler.util.CompetenceTestData.createCompetenceResponseDto;
import static by.javaguru.profiler.util.CurriculumVitaeTestData.CV_UUID;
import static by.javaguru.profiler.util.CvLanguageTestData.createCvLanguage;
import static by.javaguru.profiler.util.SkillTestData.createSkill;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetenceServiceImplTest {

    @InjectMocks
    private CompetenceServiceImpl competenceService;

    @Mock
    private CurriculumVitaeService curriculumVitaeService;

    @Mock
    private SkillService skillService;

    @Mock
    private CvLanguageMapper cvLanguageMapper;

    @Mock
    private CompetenceMapper competenceMapper;

    @Test
    void shouldSaveCompetenciesToCvWhenInvokeSave() {
        CompetenceRequestDto competenceRequestDto = createCompetenceRequestDto();
        CompetenceResponseDto competenceResponseDto = createCompetenceResponseDto();
        List<Long> skillIds = competenceRequestDto.skills();
        Skill skill = createSkill();
        List<Skill> skillList = List.of(skill);
        CvLanguage cvLanguage = createCvLanguage().build();
        List<CvLanguage> cvLanguageList = Stream.generate(() -> cvLanguage).limit(skillIds.size()).toList();

        when(skillService.getListOfSkillsByIds(skillIds)).thenReturn(skillList);
        when(cvLanguageMapper.fromDtoToEntity(isA(CvLanguageRequestDto.class))).thenReturn(cvLanguage);
        when(competenceMapper.fromEntitiesToDto(skillList, cvLanguageList)).thenReturn(competenceResponseDto);

        assertDoesNotThrow(() -> competenceService.save(competenceRequestDto, CV_UUID));
    }

}
