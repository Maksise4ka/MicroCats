package com.maksise4ka.microcats.contracts.owners.requests;

import java.time.LocalDate;

public record ChangeBirthdate(
        Long ownerId,
        LocalDate birthdate,
        String requesterUsername) {
}
