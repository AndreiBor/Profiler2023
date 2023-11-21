package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.MainEducation;
import by.javaguru.profiler.usecasses.dto.MainEducationRequestDto;
import by.javaguru.profiler.usecasses.dto.MainEducationResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface MainEducationMapper {

    @Mapping(target = "id", ignore = true)
    MainEducation fromDtoToEntity(MainEducationRequestDto requestDto);

    MainEducationResponseDto fromEntityToDto(MainEducation entity);
}
