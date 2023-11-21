package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Skill;
import by.javaguru.profiler.usecasses.dto.SkillResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, builder = @Builder(disableBuilder = true))
public interface SkillMapper {

    SkillResponseDto fromEntityToDto(Skill skill);

}
