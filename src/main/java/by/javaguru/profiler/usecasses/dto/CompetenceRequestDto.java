package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.usecasses.annotation.LanguageListValidation;
import by.javaguru.profiler.usecasses.annotation.SkillsListValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder(setterPrefix = "with")
public record CompetenceRequestDto(
        @LanguageListValidation
        @NotEmpty(message = "List of languages must have at least 1 language")
        @Size(max = 6, message = "Amount of languages should not be more than 6")
        @Valid
        List<@NotNull(message = "Language must not be null") CvLanguageRequestDto> languages,
        @SkillsListValidation
        @NotEmpty(message = "List of skills must have at least 1 skill")
        @Size(max = 15, message = "Amount of skills should not be more than 15")
        List<@NotNull(message = "Skill id must not be null") Long> skills
) {
}
