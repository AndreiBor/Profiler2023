package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Institution;
import by.javaguru.profiler.usecasses.dto.InstitutionRequestDto;
import by.javaguru.profiler.usecasses.dto.InstitutionResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface InstitutionMapper {
    InstitutionResponseDto fromEntityToDto(Institution institution);

    Institution fromDtoToEntity(InstitutionRequestDto dto);
}
