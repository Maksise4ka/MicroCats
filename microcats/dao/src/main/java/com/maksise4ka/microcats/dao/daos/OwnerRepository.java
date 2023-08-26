package com.maksise4ka.microcats.dao.daos;

import com.maksise4ka.microcats.dao.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByUserLogin(String username);
}
