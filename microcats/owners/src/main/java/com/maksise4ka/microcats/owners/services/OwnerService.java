package com.maksise4ka.microcats.owners.services;

import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.OwnerDto;
import com.maksise4ka.microcats.users.exceptions.AccessViolationException;
import com.maksise4ka.microcats.users.exceptions.AuthorizationException;

import java.time.LocalDate;
import java.util.Collection;

public interface OwnerService {
    OwnerDto create(String name, LocalDate birthdate, String login, String password);
    CatDto petCat(String name, LocalDate birthdate, String breed, String color, Long ownerId, String userLogin);

    void changeName(Long id, String newName, String userLogin);
    void changeBirthdate(Long id, LocalDate newBirthdate, String userLogin);

    OwnerDto get(Long id, String userLogin) throws AuthorizationException, AccessViolationException;
    Collection<OwnerDto> getAll(String userLogin);

    void delete(Long id, String userLogin);
}
