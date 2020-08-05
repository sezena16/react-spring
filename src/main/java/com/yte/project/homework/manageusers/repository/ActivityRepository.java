package com.yte.project.homework.manageusers.repository;

import com.yte.project.homework.manageusers.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByActivityName(String activityName);

    @Transactional
    @Modifying
    @Query(value = "delete from Activity a where a.activityName = :activityName")
    void deleteActivitiesByActivityName(@Param("activityName") String activityName);

}
