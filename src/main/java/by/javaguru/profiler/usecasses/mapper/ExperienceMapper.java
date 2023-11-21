package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Experience;
import by.javaguru.profiler.usecasses.IndustryService;
import by.javaguru.profiler.usecasses.dto.ExperienceRequestDto;
import by.javaguru.profiler.usecasses.dto.ExperienceResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class ExperienceMapper {

    @Autowired
    protected IndustryService industryService;

    @Mapping(target = "industryId", source = "experience.industry.id")
    @Mapping(target = "industryName", source = "experience.industry.name")
    public abstract ExperienceResponseDto fromEntityToDto(Experience experience);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "industry", expression = "java(industryService.getIndustryById(experienceRequestDto.industryId()))")
    public abstract Experience fromDtoToEntity(ExperienceRequestDto experienceRequestDto);
}
