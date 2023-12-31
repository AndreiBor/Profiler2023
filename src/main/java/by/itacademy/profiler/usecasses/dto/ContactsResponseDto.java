package by.itacademy.profiler.usecasses.dto;

import lombok.Builder;

import java.io.Serializable;

/**
 * A DTO for the {@link by.itacademy.profiler.persistence.model.Contacts} entity
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