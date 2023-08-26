package com.maksise4ka.microcats.controller.dtos.friendship;

import jakarta.validation.constraints.NotNull;

public record MakeFriendsDto(
        @NotNull Long id1,
        @NotNull Long id2) {
}
