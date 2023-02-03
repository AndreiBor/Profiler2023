package by.itacademy.profiler.usecasses.mapper;

import by.itacademy.profiler.persistence.model.Contacts;
import by.itacademy.profiler.persistence.model.CurriculumVitae;
import by.itacademy.profiler.usecasses.dto.CurriculumVitaeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurriculumVitaeMapper {

    @Mapping(target = "isContactsExists", source = "contacts")
    @Mapping(target = "imageUuid", source = "image.uuid")
    @Mapping(target = "positionId", source = "position.id")
    @Mapping(target = "position", source = "position.name")
    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "country", source = "country.countryName")
    CurriculumVitaeResponseDto curriculumVitaeToCurriculumVitaeResponseDto(CurriculumVitae curriculumVitae);

    List<CurriculumVitaeResponseDto> curriculumVitaeListToCurriculumVitaeResponseDtoList(List<CurriculumVitae> curriculumVitaeList);

    default boolean map(Contacts contacts) {
        return !(contacts == null);
    }
}