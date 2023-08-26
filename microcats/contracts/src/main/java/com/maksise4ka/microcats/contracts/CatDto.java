package com.maksise4ka.microcats.contracts;

import com.maksise4ka.microcats.dao.entities.Cat;

import java.time.LocalDate;

public record CatDto(Long id, String name, LocalDate birthdate, String color, String breed, Long ownerId) {
    public static CatDto asDto(Cat cat) {
        return new CatDto(cat.getId(),
                cat.getName(),
                cat.getBirthdate(),
                cat.getColor().toString(),
                cat.getBreed().toString(),
                cat.getOwner().getId()
        );
    }
}
