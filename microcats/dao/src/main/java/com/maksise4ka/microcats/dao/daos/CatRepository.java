package com.maksise4ka.microcats.dao.daos;

import com.maksise4ka.microcats.dao.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
