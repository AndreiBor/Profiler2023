package by.javaguru.profiler.usecasses.dto;

import by.javaguru.profiler.persistence.model.RoleNameEnum;

import java.io.Serializable;
import java.util.Set;

public record AuthenticationUserDto(String email, Set<RoleNameEnum> roleNames) implements Serializable {
}