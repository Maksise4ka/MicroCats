package com.maksise4ka.microcats.contracts.cats.requests;

public record ChangeCatName(Long catId, String newName, String requesterUsername) {
}
