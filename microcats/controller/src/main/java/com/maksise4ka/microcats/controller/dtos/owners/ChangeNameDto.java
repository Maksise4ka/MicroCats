package com.maksise4ka.microcats.controller.dtos.owners;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeNameDto(@NotNull @NotBlank String name) {
}
