package by.javaguru.profiler.usecasses.annotation;

import by.javaguru.profiler.usecasses.PositionService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PositionValidator implements ConstraintValidator<PositionValidation, Long> {

    private final PositionService positionService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext cxt) {
        if (id == null) {
            return true;
        }
        return positionService.isPositionExist(id);
    }
}