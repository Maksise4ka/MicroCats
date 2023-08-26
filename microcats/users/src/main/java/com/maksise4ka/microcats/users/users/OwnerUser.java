package com.maksise4ka.microcats.users.users;

import com.maksise4ka.microcats.dao.entities.Cat;
import com.maksise4ka.microcats.dao.entities.Owner;

import java.util.Objects;

/**
 * Пользователь, авторизованный как владелец котов, имеет право читать и изменять данные
 * только его котов (то есть также и друзей) и свои же
 */
public class OwnerUser implements CurrentUser {
    private final Owner owner;
    private final String username;

    public OwnerUser(Owner owner, String username) {
        this.owner = owner;
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean HasAccessToCat(Cat cat) {
        return owner.getCats().stream().anyMatch(c -> Objects.equals(c.getId(), cat.getId()));
    }

    @Override
    public boolean HasAccessToOwner(Owner owner) {
        return Objects.equals(this.owner.getId(), owner.getId());
    }

    @Override
    public boolean CanEditCat(Cat cat) {
        return HasAccessToCat(cat);
    }

    @Override
    public boolean CanEditOwner(Owner owner) {
        return HasAccessToOwner(owner);
    }

    @Override
    public boolean CanPetCat(Long ownerId) {
        return Objects.equals(ownerId, owner.getId());
    }
}
