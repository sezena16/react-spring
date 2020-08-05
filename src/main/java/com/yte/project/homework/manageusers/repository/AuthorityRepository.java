package com.yte.project.homework.manageusers.repository;

import com.yte.project.homework.manageusers.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
