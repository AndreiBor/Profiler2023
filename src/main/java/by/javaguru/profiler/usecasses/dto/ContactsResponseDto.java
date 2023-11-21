package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.Contacts;
import lombok.Builder;

import java.io.Serializable;

/**
 * A DTO for the {@link Contacts} entity
 */
@Builder(setterPrefix = "with")
public record ContactsResponseDto(Long phoneCodeId,
                                  Integer phoneCode,
                                  String phoneNumber,
                                  String email,
                                  String skype,
                                  String linkedin,
                                  String portfolio) implements Serializable {
}