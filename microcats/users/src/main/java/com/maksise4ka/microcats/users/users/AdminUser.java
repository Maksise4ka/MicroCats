package com.maksise4ka.microcats.users.users;

import com.maksise4ka.microcats.dao.entities.Cat;
import com.maksise4ka.microcats.dao.entities.Owner;

/**
 * Пользователь, имеющий доступ ко всему, то есть админ
 *
 * @param username имя админа
 */
public record AdminUser(String username) implements CurrentUser {

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean HasAccessToCat(Cat cat) {
        return true;
    }

    @Override
    public boolean HasAccessToOwner(Owner owner) {
        return true;
    }

    @Override
    public boolean CanEditCat(Cat cat) {
        return true;
    }

    @Override
    public boolean CanEditOwner(Owner owner) {
        return true;
    }

    @Override
    public boolean CanPetCat(Long ownerId) {
        return true;
    }
}
