package com.yte.project.homework.manageusers;

import com.yte.project.homework.manageusers.entity.Activity;
import com.yte.project.homework.manageusers.entity.Authority;
import com.yte.project.homework.manageusers.entity.OutsideUser;
import com.yte.project.homework.manageusers.entity.Users;
import com.yte.project.homework.manageusers.repository.ActivityRepository;
import com.yte.project.homework.manageusers.repository.OutsideUserRepository;
import com.yte.project.homework.manageusers.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ManageUserService {

    private final UsersRepository usersRepository;
    private final ActivityRepository activityRepository;
    private final CustomUserDetailsManager customUserDetailsManager;
    private final OutsideUserRepository outsideUserRepository;

    public List<Users> listAllStudents() {
        return usersRepository.findAll();
    }

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public List<Activity> getUsersActivity(String username) {
        return usersRepository.findByUsername(username).map(Users::getActivities)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Users addUser(Users users) {
        users.setAuthorities(List.of(new Authority("READ"),new Authority("WRITE")));
        customUserDetailsManager.createUser(users);
        return users;
    }

    @Transactional
    public Users updateUser(String username, Users users) {
        Optional<Users> userOptional = usersRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            updateUsersFromDB(users, userOptional.get());
            return usersRepository.save(users);
        } else {
            throw new EntityNotFoundException();
        }

    }

    private void updateUsersFromDB(Users users, Users usersFromDB) {
        usersFromDB.setUsername(users.getUsername());
        usersFromDB.setPassword(users.getPassword());
    }

    public void deleteUser(String username) {
        usersRepository.deleteByUsername(username);
    }

    public OutsideUser addOutsideUser(OutsideUser outsideUser) {
        Optional<OutsideUser> outsideUserOptional=outsideUserRepository.findByName(outsideUser.getName());
        if(!outsideUserOptional.isPresent())
            outsideUserRepository.save(outsideUser);
        return outsideUser;
    }

    public List<OutsideUser> getOutsideUsers(String activityName) {
        Optional<Activity> activityOptional=activityRepository.findByActivityName(activityName);
        List<OutsideUser> outsideUserList=new ArrayList<OutsideUser>();
        if(activityOptional.isPresent()){
            Activity activity=activityOptional.get();
            outsideUserList.addAll(activity.getOutsideUserActivities());
        }
        return outsideUserList;
    }

    public List<Activity> getOutsideUsersActivity() {
        List<Activity> activityList =new ArrayList<Activity>();
        List<Activity> lastActivityList =new ArrayList<Activity>();
        activityList.addAll(activityRepository.findAll());
        for(int i=0;i<activityList.size();i++){
            Optional<Activity> activityOptional=activityRepository.findByActivityName(activityList.get(i).getActivityName());
            Activity activity=activityOptional.get();
            if (!activity.isStartDatePast()) {
                lastActivityList.add(activity);
            }
        }
        return lastActivityList;
    }

    public List<Activity> getUsersActivity() {
        List<Activity> activityList =new ArrayList<Activity>();
        List<Activity> lastActivityList =new ArrayList<Activity>();
        activityList.addAll(activityRepository.findAll());
        for(int i=0;i<activityList.size();i++){
            Optional<Activity> activityOptional=activityRepository.findByActivityName(activityList.get(i).getActivityName());
            Activity activity=activityOptional.get();
            lastActivityList.add(activity);
        }
        return lastActivityList;
    }

    @Transactional
    public Activity addOutsideUsertoActivity(String trIdNo,String activityName,Activity activity) {
        Optional<OutsideUser> outsideUserOptional = outsideUserRepository.findByTrIdNo(trIdNo);
        Optional<Activity> activityOptional=activityRepository.findByActivityName(activityName);
        Activity activitymain=activityOptional.get();
        List<Users> usersList=activitymain.getUsersActivities();
        if(activitymain.getOutsideUserActivities()==null){
            if (outsideUserOptional.isPresent()) {
                deleteActivity(activityName);
                activity.setUsersActivities(usersList);
                OutsideUser outsideUser = outsideUserOptional.get();
                if(activitymain.getOutsideUserActivities().contains(outsideUser))
                    throw new IllegalStateException("The user with this TR ID Number is already exists!");
                activity.setActivityName(activitymain.getActivityName());
                activity.setStartDate(activitymain.getStartDate());
                activity.setEndDate(activitymain.getEndDate());
                activity.setCapacity(activitymain.getCapacity());
                activity.setLatitude(activitymain.getLatitude());
                activity.setLongitude(activitymain.getLongitude());
                List<Activity> activityList= new ArrayList<Activity>();
                List<OutsideUser> outsideUserList= new ArrayList<OutsideUser>();
                outsideUserList.add(outsideUser);
                activityList.addAll(outsideUser.getOutsideActivities());
                activityList.add(activity);
                outsideUser.setOutsideActivities(activityList);
                activity.setOutsideUserActivities(outsideUserList);
                activity.setRegistered(activity.getRegistered()+1);
                for(int i=0;i<usersList.size();i++){
                    List<Activity> activityList2=new ArrayList<Activity>();
                    activityList2.addAll(usersList.get(i).getActivities());
                    activityList2.add(activity);
                    usersList.get(i).setActivities(activityList2);
                }
                if (!activity.capacityCheck()) {
                    throw new IllegalStateException("Capacity has been reached!");
                }
                if (!activity.DateValidation()) {
                    throw new IllegalStateException("The Date is not valid!");
                }
                List<Users> savedUser = usersRepository.saveAll(usersList);
                OutsideUser savedOutsideUser = outsideUserRepository.save(outsideUser);
                return savedOutsideUser
                        .getOutsideActivities()
                        .stream()
                        .filter(it -> it.getActivityName().equals(activity.getActivityName()))
                        .collect(toList())
                        .get(0);
            } else {
                throw new EntityNotFoundException();
            }
        }
        else {
            if (outsideUserOptional.isPresent()) {
                deleteActivity(activityName);
                activity.setUsersActivities(usersList);
                OutsideUser outsideUser = outsideUserOptional.get();
                activity.setActivityName(activitymain.getActivityName());
                activity.setStartDate(activitymain.getStartDate());
                activity.setEndDate(activitymain.getEndDate());
                activity.setCapacity(activitymain.getCapacity());
                activity.setLatitude(activitymain.getLatitude());
                activity.setLongitude(activitymain.getLongitude());
                activity.setRegistered(activitymain.getRegistered()+1);
                List<OutsideUser> outsideUserList = new CopyOnWriteArrayList<OutsideUser>();
                if(activitymain.getOutsideUserActivities().contains(outsideUser))
                    throw new IllegalStateException("The user with this TR ID Number is already exists!");
                if(!activitymain.getOutsideUserActivities().contains(outsideUser))
                    outsideUserList.add(outsideUser);
                outsideUserList.addAll(activitymain.getOutsideUserActivities());
                for (int i = 0; i < outsideUserList.size(); i++) {
                    List<Activity> activities = new ArrayList<Activity>();
                    activities.addAll(outsideUserList.get(i).getOutsideActivities());
                    activities.add(activity);
                    outsideUserList.get(i).setOutsideActivities(activities);
                }
                activity.setOutsideUserActivities(outsideUserList);
                for(int i=0;i<usersList.size();i++){
                    List<Activity> activityList=new ArrayList<Activity>();
                    activityList.addAll(usersList.get(i).getActivities());
                    activityList.add(activity);
                    usersList.get(i).setActivities(activityList);
                }
                if (!activity.capacityCheck()) {
                    throw new IllegalStateException("Capacity has been reached!");
                }
                if (!activity.DateValidation()) {
                    throw new IllegalStateException("The Date is not valid!");
                }
                List<Users> savedUser = usersRepository.saveAll(usersList);
                List<OutsideUser> savedOutsideUser = outsideUserRepository.saveAll(outsideUserList);
                return savedOutsideUser
                        .get(0)
                        .getOutsideActivities()
                        .stream()
                        .filter(it -> it.getActivityName().equals(activity.getActivityName()))
                        .collect(toList())
                        .get(0);
            } else {
                throw new EntityNotFoundException();
            }
        }
    }

    @Transactional
    public Activity addActivityToUsers(String username, Activity activity) {
        Optional<Users> usersOptional = usersRepository.findByUsername(username);
        List<OutsideUser> outsideUserList=new CopyOnWriteArrayList<OutsideUser>();
        if(activity.getUsersActivities()==null){
            if (usersOptional.isPresent()) {
                Users users = usersOptional.get();
                List<Activity> activityList= new ArrayList<Activity>();
                activityList.addAll(users.getActivities());
                activityList.add(activity);
                users.setActivities(activityList);
                activity.setUsersActivities(List.of(users));
                if(activity.getOutsideUserActivities()!=null){
                    outsideUserList.addAll(activity.getOutsideUserActivities());
                    for (int i=0;i<outsideUserList.size();i++){
                        List<Activity> activities = new ArrayList<Activity>();
                        activities.addAll(outsideUserList.get(i).getOutsideActivities());
                        activities.add(activity);
                        outsideUserList.get(i).setOutsideActivities(activities);
                    }
                    activity.setOutsideUserActivities(outsideUserList);
                }
                if (!activity.capacityCheck()) {
                    throw new IllegalStateException("Capacity has been reached!");
                }
                if (!activity.DateValidation()) {
                    throw new IllegalStateException("The Date is not valid!");
                }
                Users savedUser = usersRepository.save(users);
                return savedUser
                        .getActivities()
                        .stream()
                        .filter(it -> it.getActivityName().equals(activity.getActivityName()))
                        .collect(toList())
                        .get(0);
            } else {
                throw new EntityNotFoundException();
            }
        }
        else {
            if (usersOptional.isPresent()) {
                Users users = usersOptional.get();
                List<Users> usersList = new CopyOnWriteArrayList<Users>();
                usersList.addAll(activity.getUsersActivities());
                for (int i = 0; i < usersList.size(); i++) {
                    List<Activity> activities = new ArrayList<Activity>();
                    activities.addAll(usersList.get(i).getActivities());
                    activities.add(activity);
                    usersList.get(i).setActivities(activities);
                }
                activity.setUsersActivities(usersList);
                if(activity.getOutsideUserActivities()!=null){
                    outsideUserList.addAll(activity.getOutsideUserActivities());
                    for (int i=0;i<outsideUserList.size();i++){
                        List<Activity> activities = new ArrayList<Activity>();
                        activities.addAll(outsideUserList.get(i).getOutsideActivities());
                        activities.add(activity);
                        outsideUserList.get(i).setOutsideActivities(activities);
                    }
                    activity.setOutsideUserActivities(outsideUserList);
                }
                if (!activity.capacityCheck()) {
                    throw new IllegalStateException("Capacity has been reached!");
                }
                if (!activity.DateValidation()) {
                    throw new IllegalStateException("The Date is not valid!");
                }
                List<Users> savedUser = usersRepository.saveAll(usersList);
                return savedUser
                        .get(0)
                        .getActivities()
                        .stream()
                        .filter(it -> it.getActivityName().equals(activity.getActivityName()))
                        .collect(toList())
                        .get(0);
            } else {
                throw new EntityNotFoundException();
            }
        }
    }

    @Transactional
    public Activity updateActivity(String username,String activityName, Activity activity) {
        Optional<Activity> activityOptional = activityRepository.findByActivityName(activityName);
        Optional<Users> usersOptional = usersRepository.findByUsername(username);
        if (activityOptional.isPresent()&&usersOptional.isPresent()) {
            Users users=usersOptional.get();
            Activity activity1=activityOptional.get();
            if(activity1.isStartDatePast()){
                throw new IllegalStateException();
            }
            List<Users> usersList=new ArrayList<Users>();
            List<OutsideUser> outsideUserList=new ArrayList<OutsideUser>();
            if(!activity1.getUsersActivities().contains(users))
                usersList.add(users);
            usersList.addAll(activity1.getUsersActivities());
            activity.setUsersActivities(usersList);
            if(activity1.getOutsideUserActivities()!=null){
                outsideUserList.addAll(activity1.getOutsideUserActivities());
                activity.setOutsideUserActivities(outsideUserList);
                activity.setRegistered(outsideUserList.size());
            }
            deleteActivity(activityName);
            return addActivityToUsers(username,activity);
        } else {
            throw new EntityNotFoundException();
        }

    }

    @Transactional
    public void deleteActivity( String activityname) {
        Optional<Activity> activityOptional = activityRepository.findByActivityName(activityname);
        if(activityOptional.isPresent()) {
            Activity activity=activityOptional.get();
            if(activity.isStartDatePast()){
                throw new IllegalStateException();
            }
            List<Users> usersList=activity.getUsersActivities();
            List<OutsideUser> outsideUserList=activity.getOutsideUserActivities();
            for(int j=0;j<usersList.size();j++){
                removeActivityFromUser(activityname,usersList.get(j));
            }
            for(int k=0;k<outsideUserList.size();k++){
                removeActivityFromOutsideUser(activityname,outsideUserList.get(k));
            }
            outsideUserRepository.saveAll(outsideUserList);
            usersRepository.saveAll(usersList);
            removeActivity(activityname);
        }
    }

    private void removeActivity(String activityName){
        activityRepository.deleteActivitiesByActivityName(activityName);
    }

    private void removeActivityFromUser(String activityname, Users users) {
        List<Activity> filteredActivity = users.getActivities()
                .stream()
                .filter(it -> !it.getActivityName().equals(activityname))
                .collect(toList());

        users.getActivities().clear();
        users.getActivities().addAll(filteredActivity);
    }

    private void removeActivityFromOutsideUser(String activityname, OutsideUser outsideUser) {
        List<Activity> filteredActivity = outsideUser.getOutsideActivities()
                .stream()
                .filter(it -> !it.getActivityName().equals(activityname))
                .collect(toList());

        outsideUser.getOutsideActivities().clear();
        outsideUser.getOutsideActivities().addAll(filteredActivity);
    }
}
