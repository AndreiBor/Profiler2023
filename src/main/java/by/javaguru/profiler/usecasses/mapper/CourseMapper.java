package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Course;
import by.javaguru.profiler.usecasses.dto.CourseRequestDto;
import by.javaguru.profiler.usecasses.dto.CourseResponseDto;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface CourseMapper {

    @Mapping(target = "id", ignore = true)
    Course fromDtoToEntity(CourseRequestDto requestDto);

    CourseResponseDto fromEntityToDto(Course entity);
}
