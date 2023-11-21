package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.SkillResponseDto;

import java.util.List;

public interface SkillService {

    List<SkillResponseDto> getSkills(Long positionId);

    boolean isSkillsExistByIds(List<Long> skillIds);

    List<Skill> getListOfSkillsByIds(List<Long> skillIds);

}
