package com.maksise4ka.microcats.contracts;

import com.maksise4ka.microcats.dao.entities.Owner;

import java.time.LocalDate;
import java.util.List;

public record OwnerDto(long id, String name, LocalDate birthdate, String login, List<CatDto> cats) {
    public static OwnerDto asDto(Owner owner) {
        return new OwnerDto(
                owner.getId(),
                owner.getName(),
                owner.getBirthdate(),
                owner.getUser().getLogin(),
                owner.getCats().stream().map(CatDto::asDto).toList()
        );
    }
}
