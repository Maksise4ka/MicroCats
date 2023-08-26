package com.maksise4ka.microcats.controller.dtos.cats;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ChangeCatBirthdateDto(@NotNull @Past LocalDate birthdate) {
}
