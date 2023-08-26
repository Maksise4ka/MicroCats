package com.maksise4ka.microcats.cats.services;

import com.maksise4ka.microcats.cats.exceptions.CatServiceException;
import com.maksise4ka.microcats.contracts.CatDto;

import java.time.LocalDate;
import java.util.Collection;

public interface CatService {
    void changeName(Long id, String newName, String userLogin);
    void changeBirthdate(Long id, LocalDate newBirthdate, String userLogin);

    CatDto get(Long catId, String userLogin);
    Collection<CatDto> getAll(String userLogin);

    void delete(Long id, String userLogin);

    void makeFriends(Long id1, Long id2, String userLogin) throws CatServiceException;
    void removeFriendship(Long id1, Long id2, String userLogin) throws CatServiceException;

    Collection<CatDto> showFriends(Long id1, String userLogin);

    /**
     * Выводит всех доступных пользователю котов, которые имеют цвет из colors и породу из breeds
     * @param colors список цветов, если null, то коты не фильтруются по цветам
     * @param breeds список пород, если null, то коты не фильтруются по породам
     * @param userLogin логин пользователя
     * @return список отфильтрованных котов
     */

    Collection<CatDto> filter(Collection<String> colors, Collection<String> breeds, String userLogin);
}
