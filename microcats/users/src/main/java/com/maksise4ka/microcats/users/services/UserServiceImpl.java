package com.maksise4ka.microcats.users.services;

import com.maksise4ka.microcats.dao.daos.OwnerRepository;
import com.maksise4ka.microcats.dao.daos.UserRepository;
import com.maksise4ka.microcats.dao.entities.authorize.Role;
import com.maksise4ka.microcats.dao.entities.authorize.User;
import com.maksise4ka.microcats.users.exceptions.AuthorizationException;
import com.maksise4ka.microcats.users.users.AdminUser;
import com.maksise4ka.microcats.users.users.CurrentUser;
import com.maksise4ka.microcats.users.users.OwnerUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OwnerRepository ownerRepository) {
        this.userRepository = userRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public CurrentUser getUser(Long id) {
        User user = userRepository.getReferenceById(id);

        return matchUser(user);
    }

    @Override
    public CurrentUser getUser(String login) {
        User user = userRepository.findUserByLogin(login).orElseThrow(
                () -> AuthorizationException.usernameNotFound(login));

        return matchUser(user);
    }

    @Override
    public boolean userExists(String login) {
        return userRepository.findUserByLogin(login).isEmpty();
    }

    @Override
    public User saveOwnerUser(String login, String password) {
        User user = new User(login, password, Role.USER);
        userRepository.save(user);

        return user;
    }

    private CurrentUser matchUser(User user) {
        if (user == null)
            return null;

        CurrentUser currentUser = null;
        switch (user.getRole()) {
            case USER -> currentUser = new OwnerUser(
                    ownerRepository.findByUserLogin(user.getLogin())
                            .orElseThrow(() -> AuthorizationException.ownerNotFound(user.getLogin())),
                    user.getLogin());

            case ADMIN -> currentUser = new AdminUser(user.getLogin());
        }

        return currentUser;
    }
}
