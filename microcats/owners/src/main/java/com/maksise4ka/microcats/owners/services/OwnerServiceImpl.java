package com.maksise4ka.microcats.owners.services;

import com.maksise4ka.microcats.dao.daos.CatRepository;
import com.maksise4ka.microcats.dao.daos.OwnerRepository;
import com.maksise4ka.microcats.dao.entities.Cat;
import com.maksise4ka.microcats.dao.entities.Owner;
import com.maksise4ka.microcats.dao.entities.authorize.User;
import com.maksise4ka.microcats.dao.models.Breed;
import com.maksise4ka.microcats.dao.models.Color;
import com.maksise4ka.microcats.contracts.CatDto;
import com.maksise4ka.microcats.contracts.OwnerDto;
import com.maksise4ka.microcats.users.exceptions.AccessViolationException;
import com.maksise4ka.microcats.users.services.UserService;
import com.maksise4ka.microcats.users.users.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
public class OwnerServiceImpl implements OwnerService {
    private final CatRepository catRepo;
    private final OwnerRepository ownerRepo;
    private final UserService userService;

    @Autowired
    public OwnerServiceImpl(CatRepository catRepo, OwnerRepository ownerRepo, UserService userService) {
        this.catRepo = catRepo;
        this.ownerRepo = ownerRepo;
        this.userService = userService;
    }

    @Override
    public OwnerDto create(
            String name,
            LocalDate birthdate,
            String login,
            String password) {

        if (userService.userExists(login)) {
            throw new IllegalArgumentException();
        }

        User user = userService.saveOwnerUser(login, password);

        Owner owner = new Owner(name, birthdate, user);
        ownerRepo.save(owner);

        return OwnerDto.asDto(owner);
    }

    @Override
    public CatDto petCat(String name, LocalDate birthdate, String breed, String color, Long ownerId, String userLogin) {
        throwIfHasNotAccess(u -> u.CanPetCat(ownerId), userLogin);

        Owner owner = ownerRepo.getReferenceById(ownerId);
        Cat cat = new Cat(name, birthdate, Breed.valueOf(breed), Color.valueOf(color), owner);

        return CatDto.asDto(catRepo.save(cat));
    }

    @Override
    public void changeName(Long id, String newName, String userLogin) {
        Owner owner = ownerRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.CanEditOwner(owner), userLogin);

        owner.setName(newName);
        ownerRepo.save(owner);
    }

    @Override
    public void changeBirthdate(Long id, LocalDate newBirthdate, String userLogin) {
        Owner owner = ownerRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.CanEditOwner(owner), userLogin);

        owner.setBirthdate(newBirthdate);
        ownerRepo.save(owner);
    }

    @Override
    public OwnerDto get(Long id, String userLogin) {
        Owner owner = ownerRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.HasAccessToOwner(owner), userLogin);

        return OwnerDto.asDto(owner);
    }

    @Override
    public List<OwnerDto> getAll(String userLogin) {
        CurrentUser currentUser = userService.getUser(userLogin);
        return ownerRepo.findAll()
                .stream().filter(currentUser::HasAccessToOwner)
                .map(OwnerDto::asDto).toList();
    }

    @Override
    public void delete(Long id, String userLogin) {
        Owner owner = ownerRepo.getReferenceById(id);
        throwIfHasNotAccess(u -> u.CanEditOwner(owner), userLogin);

        owner.getCats().forEach(c ->
                c.getFriends().forEach(f ->
                            f.getFriends().removeIf(self -> self.getId().equals(c.getId()))));

        owner.getCats().forEach(c -> catRepo.saveAll(c.getFriends()));
        ownerRepo.delete(owner);
    }

    private void throwIfHasNotAccess(Predicate<CurrentUser> condition, String login) {
        CurrentUser currentUser = userService.getUser(login);

        if (!condition.test(currentUser))
            throw AccessViolationException.userHasNotAccess(login);
    }
}
