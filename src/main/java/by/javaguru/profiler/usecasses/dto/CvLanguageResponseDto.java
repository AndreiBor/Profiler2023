package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.LanguageProficiencyEnum;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record CvLanguageResponseDto(
        Long id,
        String name,
        LanguageProficiencyEnum languageProficiency,
        String certificateUrl
) {
}
