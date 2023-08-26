package com.maksise4ka.microcats.controller.mappers;

import com.maksise4ka.microcats.contracts.owners.requests.*;
import com.maksise4ka.microcats.controller.dtos.owners.*;
import org.springframework.security.crypto.password.PasswordEncoder;

public class OwnersDtoMapper {

    public static CreateOwner createOwner(CreateOwnerDto command, PasswordEncoder encoder) {
        return new CreateOwner(
                command.name(),
                command.birthdate(),
                command.username(),
                encoder.encode(command.password())
        );
    }

    public static PetCat petCat(PetCatDto command, String username) {
        return new PetCat(
                command.name(),
                command.breed(),
                command.color(),
                command.birthdate(),
                command.ownerId(),
                username
        );
    }

    public static ChangeName changeName(Long ownerId, ChangeNameDto command, String username) {
        return new ChangeName(ownerId, command.name(), username);
    }

    public static ChangeBirthdate changeBirthdate(Long ownerId, ChangeBirthdateDto command, String username) {
        return new ChangeBirthdate(ownerId, command.birthdate(), username);
    }

    public static GetOwner getOwner(Long ownerId, String username) {
        return new GetOwner(ownerId, username);
    }

    public static GetAll getAll(String username) {
        return new GetAll(username);
    }

    public static DeleteOwner deleteOwner(Long ownerId, String username) {
        return new DeleteOwner(ownerId, username);
    }
}

