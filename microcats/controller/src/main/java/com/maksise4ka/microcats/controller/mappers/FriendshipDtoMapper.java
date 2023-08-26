package com.maksise4ka.microcats.controller.mappers;

import com.maksise4ka.microcats.contracts.friendship.requests.*;
import com.maksise4ka.microcats.controller.dtos.friendship.MakeFriendsDto;
import com.maksise4ka.microcats.controller.dtos.friendship.RemoveFriendshipDto;

public class FriendshipDtoMapper {

    public static MakeFriends makeFriends(MakeFriendsDto command, String username) {
        return new MakeFriends(command.id1(), command.id2(), username);
    }

    public static RemoveFriendship removeFriendship(RemoveFriendshipDto command, String username) {
        return new RemoveFriendship(command.id1(), command.id2(), username);
    }

    public static ShowFriends showFriends(Long catId, String username) {
        return new ShowFriends(catId, username);
    }
}
