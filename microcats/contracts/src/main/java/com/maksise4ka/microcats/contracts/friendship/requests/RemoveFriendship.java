package com.maksise4ka.microcats.contracts.friendship.requests;

public record RemoveFriendship(Long catId1, Long catId2, String requesterUsername) {
}
