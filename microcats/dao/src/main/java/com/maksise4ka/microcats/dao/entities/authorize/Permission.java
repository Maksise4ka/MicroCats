package com.maksise4ka.microcats.dao.entities.authorize;

import lombok.Getter;

public enum Permission {
    CATS_READ("cats:read"),
    CATS_UPDATE("cats:write"),
    CATS_CREATE("cats:create"),
    FRIENDSHIP_CREATE("friendship:create"),
    FRIENDSHIP_READ("friendship:read"),
    OWNERS_READ("owners:read"),
    OWNERS_UPDATE("owners:update"),
    USERS_CREATE("users:create");

    @Getter
    final String permission;

    Permission (String permission) {
        this.permission = permission;
    }
}
