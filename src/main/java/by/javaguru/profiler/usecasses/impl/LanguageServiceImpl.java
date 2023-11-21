package by.javaguru.profiler.usecasses.impl;

import by.javaguru.profiler.persistence.model.Language;
import by.javaguru.profiler.persistence.repository.LanguageRepository;
import by.javaguru.profiler.usecasses.LanguageService;
import by.javaguru.profiler.usecasses.dto.LanguageResponseDto;
import by.javaguru.profiler.usecasses.mapper.LanguageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<LanguageResponseDto> getLanguages() {
        List<Language> languages = languageRepository.findAllByOrderByName();

        return languages.stream()
                    .map(languageMapper::fromEntityToDto)
                    .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLanguagesExistByIds(List<Long> languageIds) {
        return languageRepository.existsAllByIds(languageIds, languageIds.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Language getLanguageById(Long languageId) {
        return languageRepository.getLanguageById(languageId);
    }
}
