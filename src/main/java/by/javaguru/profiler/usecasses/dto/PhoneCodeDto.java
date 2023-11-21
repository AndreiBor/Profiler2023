package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.Country;
import lombok.Builder;

@Builder(setterPrefix = "with")
public record PhoneCodeDto(Long id, Integer code, Country country) {
}