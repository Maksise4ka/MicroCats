package com.maksise4ka.microcats.contracts.cats.requests;

import java.time.LocalDate;

public record ChangeCatBirthdate(Long catId, LocalDate birthdate, String requesterUsername) {
}
