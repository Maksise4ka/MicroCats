package com.maksise4ka.microcats.controller.dtos.cats;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeCatNameDto(@NotNull @NotBlank String name) {
}
