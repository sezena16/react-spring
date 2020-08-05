package com.yte.project.homework.manageusers.util;

import com.yte.project.homework.manageusers.CustomUserDetailsManager;
import com.yte.project.homework.manageusers.DTO.ActivityDTO;
import com.yte.project.homework.manageusers.entity.Activity;
import com.yte.project.homework.manageusers.entity.Authority;
import com.yte.project.homework.manageusers.entity.OutsideUser;
import com.yte.project.homework.manageusers.entity.Users;
import com.yte.project.homework.manageusers.repository.ActivityRepository;
import com.yte.project.homework.manageusers.repository.AuthorityRepository;
import com.yte.project.homework.manageusers.repository.OutsideUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DatabasePopulator {

    private final CustomUserDetailsManager customUserDetailsManager;
    private final AuthorityRepository authorityRepository;
    private final ActivityRepository activityRepository;
    private final OutsideUserRepository outsideUserRepository;

    @Transactional
    public void populateDatabaseAfterInit() {

        Authority authority1=new Authority("READ");
        Authority authority2=new Authority("WRITE");
        Authority authority3=new Authority("EXECUTE");

        List<Authority> authorities=new ArrayList<Authority>();

        authorities.add(authority1);
        authorities.add(authority2);
        authorities.add(authority3);

        authorityRepository.saveAll(authorities);

        Users adminUser = new Users("admin", "admin", List.copyOf(authorities),List.of());
        Users normalUser = new Users("user", "user", List.of(authority1),List.of());
        Users sysUser = new Users("sys", "sys", List.of(),List.of());

        List<Users> users=new ArrayList<Users>();

        OutsideUser outsideUser1=new OutsideUser("Celil","Yaramaz","19049414822",List.of());
        OutsideUser outsideUser2=new OutsideUser("Celal","Haylaz","67365395622",List.of());

        List<OutsideUser> outsideUserList=new ArrayList<OutsideUser>();

        Activity activity1=new Activity(List.copyOf(users),List.copyOf(outsideUserList),"Jogging" ,
                LocalDateTime.parse("2020-08-27T00:00"),
                LocalDateTime.parse("2020-08-28T00:00"),3,43.8F,43.6F,2);

        Activity activity2=new Activity(List.copyOf(users),List.copyOf(outsideUserList),"Blending" ,
                LocalDateTime.parse("2020-08-05T19:35"),
                LocalDateTime.parse("2020-08-16T00:00"),15,45.2F,47.9F,2);

        ArrayList<Activity> activities=new ArrayList<Activity>();

        activities.add(activity1);
        activities.add(activity2);

        adminUser.setActivities(activities);
        normalUser.setActivities(activities);
        sysUser.setActivities(activities);

        outsideUserList.add(outsideUser1);
        outsideUserList.add(outsideUser2);

        outsideUser1.setOutsideActivities(activities);
        outsideUser2.setOutsideActivities(activities);

        activityRepository.saveAll(activities);
        customUserDetailsManager.createUser(adminUser);
        customUserDetailsManager.createUser(normalUser);
        customUserDetailsManager.createUser(sysUser);
        outsideUserRepository.saveAll(outsideUserList);
    }
}
