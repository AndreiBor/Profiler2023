package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.About;
import by.javaguru.profiler.usecasses.util.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link About} entity
 */
@Builder(setterPrefix = "with")
public record AboutDto(@Length(max = 450, message = "Description is too long, the max number of symbols is 450")
                       @NotNull(message = "Required field")
                       @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_DESCRIPTION, message = "Invalid  data")
                       @Schema(defaultValue = "Hi everyone! My name is Kate. I'm a beginner UX", description = "Description")
                       String description,
                       @Length(max = 255)
                       @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_NOT_BLANK_BUT_NULL, message = "Field must be filled or null")
                       @Schema(defaultValue = "https://docs.google.com/rndm", description = "Self-presentation link")
                       String selfPresentation) implements Serializable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AboutDto aboutDto = (AboutDto) o;
        return Objects.equals(description, aboutDto.description) && Objects.equals(selfPresentation, aboutDto.selfPresentation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, selfPresentation);
    }
}