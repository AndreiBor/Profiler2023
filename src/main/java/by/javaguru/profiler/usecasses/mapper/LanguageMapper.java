package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Language;
import by.javaguru.profiler.usecasses.dto.LanguageResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public interface LanguageMapper {

    LanguageResponseDto fromEntityToDto(Language language);

}
