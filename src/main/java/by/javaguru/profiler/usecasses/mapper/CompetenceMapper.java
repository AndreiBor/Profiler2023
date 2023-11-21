package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.CvLanguage;
import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.CompetenceResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = {SkillMapper.class, CvLanguageMapper.class})
public interface CompetenceMapper {

    CompetenceResponseDto fromEntitiesToDto(List<Skill> skills, List<CvLanguage> languages);

}
