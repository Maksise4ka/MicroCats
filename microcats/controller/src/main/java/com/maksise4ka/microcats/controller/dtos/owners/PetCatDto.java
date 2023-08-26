package com.maksise4ka.microcats.controller.dtos.owners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PetCatDto(
        @NotBlank @NotNull String name,
        @NotBlank @NotNull String breed,
        @NotBlank @NotNull String color,
        @Past @NotNull LocalDate birthdate,
        @NotNull Long ownerId) {
}
