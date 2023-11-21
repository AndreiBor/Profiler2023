package by.javaguru.profiler.usecasses.dto;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record SkillResponseDto(Long id, String name) {
}
