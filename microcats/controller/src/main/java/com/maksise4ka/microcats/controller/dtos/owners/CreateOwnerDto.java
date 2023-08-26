package com.maksise4ka.microcats.controller.dtos.owners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CreateOwnerDto(
        @NotBlank @NotNull String name,
        @NotNull @Past LocalDate birthdate,
        @NotNull @NotBlank String username,
        @NotNull String password) {
}
