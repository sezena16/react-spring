package com.yte.project.homework.manageusers.repository;

import com.yte.project.homework.manageusers.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);

}
