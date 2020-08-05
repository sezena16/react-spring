package com.yte.project.homework.manageusers.repository;

import com.yte.project.homework.manageusers.entity.OutsideUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OutsideUserRepository extends JpaRepository<OutsideUser, Long> {

    Optional<OutsideUser> findByName(String name);

    Optional<OutsideUser> findByTrIdNo(String trIdNo);

    void deleteByName(String name);

    boolean existsByName(String name);

}

