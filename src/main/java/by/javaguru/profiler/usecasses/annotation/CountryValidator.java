package by.javaguru.profiler.usecasses.annotation;

import by.javaguru.profiler.usecasses.CountryService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CountryValidator implements ConstraintValidator<CountryValidation, Long> {

    private final CountryService countryService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext cxt) {
        if (id == null) {
            return true;
        }
        return countryService.isCountryExist(id);
    }
}