package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.Image;

import java.io.Serializable;

/**
 * A DTO for the {@link Image} entity
 */
public record ImageDto(String uuid) implements Serializable {
}