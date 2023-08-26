package com.maksise4ka.microcats.users.users;

import com.maksise4ka.microcats.dao.entities.Cat;
import com.maksise4ka.microcats.dao.entities.Owner;

/**
 * Интерфейс пользователя, имеющий набор методов, которые определяют
 * имеет ли он доступ к каким-то операциям исходя из его роли или его агрегирующих полей
 */
public interface CurrentUser {
    String getUsername();

    boolean HasAccessToCat(Cat cat);
    boolean HasAccessToOwner(Owner owner);

    boolean CanEditCat(Cat cat);
    boolean CanEditOwner(Owner owner);

    /**
     * Метод проверки, что данный пользователь может создать кота для владельца
     * этим пользователем может быть сам владелец
     * @param ownerId идентификатор владельца, которому создается кот
     * @return true - если имеет право, иначе false
     */
    boolean CanPetCat(Long ownerId);
}
