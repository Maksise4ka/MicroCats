package com.maksise4ka.microcats.contracts.owners.requests;

import java.time.LocalDate;

public record CreateOwner(
        String name,
        LocalDate birthdate,
        String username,
        String password) {
}
