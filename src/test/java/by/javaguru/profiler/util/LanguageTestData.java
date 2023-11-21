package by.javaguru.profiler.util;

import by.javaguru.profiler.persistence.model.Language;
import by.javaguru.profiler.usecasses.dto.LanguageResponseDto;

import java.util.ArrayList;
import java.util.List;

import static by.javaguru.profiler.util.SecondaryGenerator.generateRndStr;

public final class LanguageTestData {

    public static final String LANGUAGE_URL_TEMPLATE = "/api/v1/languages";

    private LanguageTestData() {
    }

    public static Language.LanguageBuilder createLanguage() {
        return Language.builder()
                .withId(1L)
                .withName(generateRndStr(30))
                .withIsTop(true);
    }

    public static LanguageResponseDto createLanguageResponseDto() {
        return LanguageResponseDto.builder()
                .withId(1L)
                .withName("Russian")
                .withIsTop(true)
                .build();
    }

    public static List<LanguageResponseDto> createTestLanguageResponseDtoList() {
        List<LanguageResponseDto> languageResponseDtoList = new ArrayList<>();

        languageResponseDtoList.add(LanguageResponseDto.builder()
                .withId(1L)
                .withName("English")
                .withIsTop(true)
                .build());

        languageResponseDtoList.add(LanguageResponseDto.builder()
                .withId(2L)
                .withName("Spain")
                .withIsTop(false)
                .build());

        languageResponseDtoList.add(LanguageResponseDto.builder()
                .withId(3L)
                .withName("Russian")
                .withIsTop(true)
                .build());

        return languageResponseDtoList;
    }
}
