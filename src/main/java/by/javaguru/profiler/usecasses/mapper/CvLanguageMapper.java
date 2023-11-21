package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.CvLanguage;
import by.javaguru.profiler.usecasses.LanguageService;
import by.javaguru.profiler.usecasses.dto.CvLanguageRequestDto;
import by.javaguru.profiler.usecasses.dto.CvLanguageResponseDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true))
public abstract class CvLanguageMapper {

    @Autowired
    protected LanguageService languageService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "language", expression = "java(languageService.getLanguageById(cvLanguageRequestDto.id()))")
    @Mapping(target = "proficiency", source = "languageProficiency")
    public abstract CvLanguage fromDtoToEntity(CvLanguageRequestDto cvLanguageRequestDto);

    @Mapping(target = "name", source = "cvLanguage.language.name")
    @Mapping(target = "languageProficiency", source = "proficiency")
    @Mapping(target = "id", source = "cvLanguage.language.id")
    public abstract CvLanguageResponseDto fromEntityToDto(CvLanguage cvLanguage);
}
