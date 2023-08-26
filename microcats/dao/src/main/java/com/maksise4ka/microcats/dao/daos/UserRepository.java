package com.maksise4ka.microcats.dao.daos;

import com.maksise4ka.microcats.dao.entities.authorize.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);
}
