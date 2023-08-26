package com.maksise4ka.microcats.contracts.owners.requests;

import java.time.LocalDate;

public record PetCat(
        String name,
        String breed,
        String color,
        LocalDate birthdate,
        Long ownerId,
        String requesterUsername) {
}

