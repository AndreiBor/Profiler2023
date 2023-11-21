package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.persistence.model.Course;
import by.javaguru.profiler.persistence.model.CvLanguage;
import by.javaguru.profiler.persistence.model.Experience;
import by.javaguru.profiler.persistence.model.MainEducation;
import by.javaguru.profiler.persistence.model.Recommendation;
import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeRequestDto;
import by.javaguru.profiler.usecasses.dto.CurriculumVitaeResponseDto;

import java.util.List;

public interface CurriculumVitaeService {

    CurriculumVitaeResponseDto save(CurriculumVitaeRequestDto curriculumVitaeRequestDto);

    boolean isCreationCvAvailable();

    List<CurriculumVitaeResponseDto> getAllCvOfUser();

    CurriculumVitaeResponseDto getCvOfUser(String uuid);

    CurriculumVitaeResponseDto update(String curriculumVitaeUuid, CurriculumVitaeRequestDto curriculumVitaeRequestDto);

    boolean isCurriculumVitaeExists(String uuid);

    void saveSkillsToCv(String cvUuid, List<Skill> skills);

    void saveLanguagesToCv(String cvUuid, List<CvLanguage> languages);

    List<CvLanguage> getCvLanguagesByCvUuid(String cvUuid);

    List<Skill> getCvSkillsByCvUuid(String cvUuid);

    List<Experience> saveExperienceToCv(String cvUuid, List<Experience> experience);

    List<Experience> getCvExperienceByCvUuid(String cvUuid);

    List<MainEducation> saveMainEducationsToCv(String cvUuid, List<MainEducation> mainEducations);

    List<Course> saveCoursesToCv(String cvUuid, List<Course> courses);

    List<MainEducation> getMainEducationsByCvUuid(String cvUuid);

    List<Course> getCoursesByCvUuid(String cvUuid);

    List<Recommendation> saveRecommendationsToCv(String cvUuid, List<Recommendation> recommendations);

    List<Recommendation> getRecommendationsByCvUuid(String cvUuid);
}
