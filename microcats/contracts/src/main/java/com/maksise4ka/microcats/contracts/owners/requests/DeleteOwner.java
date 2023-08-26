package com.maksise4ka.microcats.contracts.owners.requests;

public record DeleteOwner(
        Long ownerId,
        String requesterUsername) {
}
