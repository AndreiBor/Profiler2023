package by.javaguru.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record PositionDto(Long id,
                          String name
) {
}
