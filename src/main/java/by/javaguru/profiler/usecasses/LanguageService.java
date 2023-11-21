package by.javaguru.profiler.usecasses;

import by.javaguru.profiler.persistence.model.Language;
import by.javaguru.profiler.usecasses.dto.LanguageResponseDto;

import java.util.List;

public interface LanguageService {

    List<LanguageResponseDto> getLanguages();

    boolean isLanguagesExistByIds(List<Long> languageIds);

    Language getLanguageById(Long languageId);

}
