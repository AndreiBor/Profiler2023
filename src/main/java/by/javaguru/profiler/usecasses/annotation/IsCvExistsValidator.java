package by.javaguru.profiler.usecasses.annotation;

import by.javaguru.profiler.api.exception.CurriculumVitaeNotFoundException;
import by.javaguru.profiler.usecasses.CurriculumVitaeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsCvExistsValidator implements ConstraintValidator<IsCvExists, String> {

    private final CurriculumVitaeService curriculumVitaeService;

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        if (curriculumVitaeService.isCurriculumVitaeExists(uuid)) {
            return true;
        } else {
            throw new CurriculumVitaeNotFoundException(String.format("CV with UUID %s not found!!!", uuid));
        }
    }
}
