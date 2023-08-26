package com.maksise4ka.microcats.dao.entities.authorize;

import java.util.Set;

public enum Role {
    USER(Set.of(
            Permission.CATS_READ,
            Permission.CATS_UPDATE,
            Permission.CATS_CREATE,
            Permission.FRIENDSHIP_CREATE,
            Permission.FRIENDSHIP_READ,
            Permission.OWNERS_UPDATE,
            Permission.OWNERS_READ)),
    ADMIN(Set.of(
            Permission.CATS_READ,
            Permission.CATS_UPDATE,
            Permission.CATS_CREATE,
            Permission.FRIENDSHIP_CREATE,
            Permission.FRIENDSHIP_READ,
            Permission.OWNERS_READ,
            Permission.USERS_CREATE,
            Permission.OWNERS_UPDATE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}
