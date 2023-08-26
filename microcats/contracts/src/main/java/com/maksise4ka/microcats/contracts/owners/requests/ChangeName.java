package com.maksise4ka.microcats.contracts.owners.requests;

public record ChangeName(
        Long ownerId,
        String name,
        String requesterUsername) {
}
