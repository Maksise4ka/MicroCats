package com.maksise4ka.microcats.controller.mappers;

import com.maksise4ka.microcats.contracts.cats.requests.*;
import com.maksise4ka.microcats.controller.dtos.cats.*;

import java.util.ArrayList;

public class CatsDtoMapper {
    public static ChangeCatName changeName(Long catId, ChangeCatNameDto command, String username) {
        return new ChangeCatName(catId, command.name(), username);
    }

    public static ChangeCatBirthdate changeBirthdate(Long catId, ChangeCatBirthdateDto command, String username) {
        return new ChangeCatBirthdate(catId, command.birthdate(), username);
    }

    public static GetCat get(Long catId, String username) {
        return new GetCat(catId, username);
    }

    public static GetAllCats getAll(String username) {
        return new GetAllCats(username);
    }

    public static DeleteCat delete(Long catId, String username) {
        return new DeleteCat(catId, username);
    }

    public static FilterCats filter(ArrayList<String> colors, ArrayList<String> breeds, String username) {
        return new FilterCats(colors, breeds, username);
    }
}
