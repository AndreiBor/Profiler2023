package by.javaguru.profiler.usecasses.mapper;

import by.javaguru.profiler.persistence.model.Language;
import by.javaguru.profiler.usecasses.dto.LanguageResponseDto;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static by.javaguru.profiler.util.LanguageTestData.createLanguage;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageMapperTest {

    private final LanguageMapper languageMapper = Mappers.getMapper(LanguageMapper.class);

    @Test
    void shouldMapCorrectlyAllFieldWhenInvokeFromEntityToDto() {
        Language language = createLanguage().withId(1L).build();
        LanguageResponseDto languageResponseDto = languageMapper.fromEntityToDto(language);
        assertEquals(language.getId(),languageResponseDto.id());
        assertEquals(language.getName(),languageResponseDto.name());
        assertEquals(language.getIsTop(),languageResponseDto.isTop());
    }

}
