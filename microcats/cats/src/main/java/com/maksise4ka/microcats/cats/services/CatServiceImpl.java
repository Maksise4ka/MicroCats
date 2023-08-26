package com.maksise4ka.microcats.cats.services;

import com.maksise4ka.microcats.cats.exceptions.CatServiceException;
import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.dao.daos.CatRepository;
import com.maksise4ka.microcats.dao.entities.Cat;
import com.maksise4ka.microcats.dao.models.Breed;
import com.maksise4ka.microcats.dao.models.Color;
import com.maksise4ka.microcats.users.exceptions.AccessViolationException;
import com.maksise4ka.microcats.users.services.UserService;
import com.maksise4ka.microcats.users.users.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Service
@Transactional
public class CatServiceImpl implements CatService {
    private final CatRepository catRepo;
    private final UserService userService;

    @Autowired
    public CatServiceImpl(CatRepository catRepo, UserService userService) {
        this.catRepo = catRepo;
        this.userService = userService;
    }

    @Override
    public void changeName(Long id, String newName, String userLogin) {
        Cat cat = catRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToCat(cat), userLogin);

        cat.setName(newName);
        catRepo.save(cat);
    }

    @Override
    public void changeBirthdate(Long id, LocalDate newBirthdate, String userLogin) {
        Cat cat = catRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToCat(cat), userLogin);

        cat.setBirthdate(newBirthdate);
        catRepo.save(cat);
    }

    @Override
    public CatDto get(Long id, String userLogin) {
        Cat cat = catRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToCat(cat), userLogin);

        return CatDto.asDto(cat);
    }

    @Override
    public List<CatDto> getAll(String userLogin) {
        CurrentUser currentUser = userService.getUser(userLogin);

        return catRepo.findAll()
                .stream().filter(currentUser::HasAccessToCat)
                .map(CatDto::asDto).toList();
    }

    @Override
    public void delete(Long id, String userLogin) {
        Cat cat = catRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToCat(cat), userLogin);

        cat.getFriends().forEach(f ->
                    f.getFriends().removeIf(self -> self.getId().equals(cat.getId())));

        catRepo.saveAll(cat.getFriends());
        catRepo.delete(cat);
    }

    /**
     * Метод создания дружбы между двумя котами
     * дружбу может создать только тот пользователь, который
     * имеет доступ хотя бы к одному из котов
     * @param id1 идентификатор первого кота
     * @param id2 идентификатор второго котаю
     * @param userLogin логин пользователя
     * @throws CatServiceException коты уже друзья, или id1 = id2
     */
    public void makeFriends(Long id1, Long id2, String userLogin) throws CatServiceException {
        List<Cat> twoCats = validate(id1, id2, userLogin);
        Cat cat1 = twoCats.get(0);
        Cat cat2 = twoCats.get(1);

        if (cat1.getFriends().stream().anyMatch(c -> Objects.equals(c.getId(), cat2.getId())))
            throw CatServiceException.AlreadyFriends(id1, id2);

        if (cat2.getFriends().stream().anyMatch(c -> Objects.equals(c.getId(), cat1.getId())))
            throw CatServiceException.AlreadyFriends(id2, id1);

        cat1.getFriends().add(cat2);
        cat2.getFriends().add(cat1);

        catRepo.save(cat1);
        catRepo.save(cat2);
    }

    public void removeFriendship(Long id1, Long id2, String userLogin) throws CatServiceException {
        List<Cat> twoCats = validate(id1, id2, userLogin);
        Cat cat1 = twoCats.get(0);
        Cat cat2 = twoCats.get(1);

        cat1.getFriends().remove(getFriend(cat1, cat2));
        cat2.getFriends().remove(getFriend(cat2, cat1));

        catRepo.save(cat1);
        catRepo.save(cat2);
    }

    @Override
    public List<CatDto> showFriends(Long id, String userLogin) {
        Cat cat = catRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToCat(cat), userLogin);

        return cat.getFriends().stream().map(CatDto::asDto).toList();
    }

    @Override
    public Collection<CatDto> filter(Collection<String> colors, Collection<String> breeds, String userLogin) {
        CurrentUser currentUser = userService.getUser(userLogin);

        Stream<Cat> allCats = catRepo.findAll().stream().filter(currentUser::HasAccessToCat);

        if (colors != null) {
            List<Color> enumColors = colors.stream().map(Color::valueOf).toList();
            allCats = allCats.filter(c -> enumColors.contains(c.getColor()));
        }

        if (breeds != null) {
            List<Breed> enumBreeds = breeds.stream().map(Breed::valueOf).toList();
            allCats = allCats.filter(c -> enumBreeds.contains(c.getBreed()));
        }

        return allCats.map(CatDto::asDto).toList();
    }

    private Cat getFriend(Cat cat, Cat friendCat) {
        return cat
                .getFriends()
                .stream().filter(c -> Objects.equals(c.getId(), friendCat.getId()))
                .findFirst()
                .orElseThrow();
    }

    private void throwIfHasNotAccess(Predicate<CurrentUser> condition, String login) {
        CurrentUser currentUser = userService.getUser(login);

        if (!condition.test(currentUser))
            throw AccessViolationException.userHasNotAccess(login);
    }

    /**
     * Безовая проверка, что операцию, связанную с дружбой выполнить возможно
     * @param id1 идентификатор первого кота
     * @param id2 идентификатор второго кота
     * @param userLogin логин пользователя
     * @return коты, соответствующие идентификаторам id1 и id2
     */
    private List<Cat> validate(Long id1, Long id2, String userLogin) throws CatServiceException {
        if (Objects.equals(id1, id2))
            throw CatServiceException.SameId(id1);

        CurrentUser currentUser = userService.getUser(userLogin);
        Cat cat1 = catRepo.getReferenceById(id1);
        Cat cat2 = catRepo.getReferenceById(id2);

        if (!currentUser.HasAccessToCat(cat1) && !currentUser.HasAccessToCat(cat2))
            throw AccessViolationException.userHasNotAccess(userLogin);

        return List.of(cat1, cat2);
    }
}
