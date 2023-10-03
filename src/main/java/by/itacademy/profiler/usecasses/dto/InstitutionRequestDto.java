package by.itacademy.profiler.usecasses.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import static by.itacademy.profiler.usecasses.util.ValidationConstants.REGEXP_VALIDATE_INSTITUTION;

@Builder(setterPrefix = "with")
public record InstitutionRequestDto(   @NotNull(message = "Institution name must not be null")
                                       @Pattern(regexp = REGEXP_VALIDATE_INSTITUTION, message = "Invalid institution name")
                                       @Length(max = 100, message = "Institution name is too long, the max number of symbols is 100")
                                       @Schema(defaultValue = "Institution Name", description = "Institution Name")
                                       String name) {
}