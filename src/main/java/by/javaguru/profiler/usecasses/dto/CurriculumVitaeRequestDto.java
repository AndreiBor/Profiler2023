package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.usecasses.annotation.CountryValidation;
import by.javaguru.profiler.usecasses.annotation.PositionValidation;
import by.javaguru.profiler.usecasses.annotation.UserImageValidation;
import by.javaguru.profiler.usecasses.util.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Builder(setterPrefix = "with")
public record CurriculumVitaeRequestDto(
        @UserImageValidation(toValidate = UserImageValidation.ValidatedDto.CV_DTO)
        @Schema(defaultValue = "601f41a2-f850-4e7a-b1ac-096f3202dfcd", description = "Image uuid")
        String imageUuid,
        @Length(max = 50, message = "Maximum length of name is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_NAME, message = "Invalid name")
        @Schema(defaultValue = "Albus", description = "User name")
        String name,
        @Length(max = 50, message = "Maximum length of surname is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_SURNAME, message = "Invalid surname")
        @Schema(defaultValue = "Dumbledore", description = "User surname")
        String surname,
        @NotNull
        @PositionValidation
        @Schema(defaultValue = "1", description = "Position id")
        Long positionId,
        @NotNull
        @CountryValidation
        @Schema(defaultValue = "1", description = "Country id")
        Long countryId,
        @Length(max = 50, message = "Maximum length of city name is 50 symbols")
        @NotNull(message = "Field must to be filled")
        @Pattern(regexp = ValidationConstants.REGEXP_VALIDATE_CITY, message = "Invalid city name")
        @Schema(defaultValue = "Minsk", description = "City name")
        String city,
        Boolean isReadyToRelocate,
        Boolean isReadyForRemoteWork) implements Serializable {
}