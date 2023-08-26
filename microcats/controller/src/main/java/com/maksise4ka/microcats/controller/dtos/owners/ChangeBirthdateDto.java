package com.maksise4ka.microcats.controller.dtos.owners;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ChangeBirthdateDto(@NotNull @Past LocalDate birthdate) {
}
