package com.yte.project.homework.manageusers;

import com.yte.project.homework.manageusers.DTO.ActivityDTO;
import com.yte.project.homework.manageusers.DTO.OutsideUserDTO;
import com.yte.project.homework.manageusers.DTO.UsersDTO;
import com.yte.project.homework.manageusers.entity.Activity;
import com.yte.project.homework.manageusers.entity.OutsideUser;
import com.yte.project.homework.manageusers.entity.Users;
import com.yte.project.homework.manageusers.mapper.ActivityMapper;
import com.yte.project.homework.manageusers.mapper.OutsideUserMapper;
import com.yte.project.homework.manageusers.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class ManageUserController {

    private final ManageUserService manageUserService;
    private final UserMapper userMapper;
    private final ActivityMapper activityMapper;
    private final OutsideUserMapper outsideUserMapper;

    @GetMapping
    public List<UsersDTO> listAllStudents() {
        List<Users> user = manageUserService.listAllStudents();
        return userMapper.mapToDto(user);
    }

    @GetMapping("/{username}")
    public UsersDTO getUserByUsername(@PathVariable String username) {
        Users user = manageUserService.getUserByUsername(username);
        return userMapper.mapToDto(user);
    }

    @PostMapping
    public UsersDTO addUser(@Valid @RequestBody UsersDTO userDTO) {
        Users user = userMapper.mapToEntity(userDTO);
        Users addedusers = manageUserService.addUser(user);
        return userMapper.mapToDto(addedusers);
    }

    @PutMapping("/{username}")
    public UsersDTO updateUser(@PathVariable String username, @Valid @RequestBody UsersDTO userDTO) {
        Users users = userMapper.mapToEntity(userDTO);
        Users updateduser = manageUserService.updateUser(username, users);
        return userMapper.mapToDto(updateduser);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username) {
        manageUserService.deleteUser(username);
    }

    @PostMapping("/outsideuser")
    public OutsideUserDTO addOutsideUser(@Valid @RequestBody OutsideUserDTO outsideUserDTO) {
        OutsideUser outsideUser = outsideUserMapper.mapToEntity(outsideUserDTO);
        OutsideUser addedusers = manageUserService.addOutsideUser(outsideUser);
        return outsideUserMapper.mapToDto(addedusers);
    }

    @GetMapping("/activities")
    public List<ActivityDTO> getOutsideUsersActivity() {
        List<Activity> usersactivity = manageUserService.getOutsideUsersActivity();
        return activityMapper.mapToDto(new ArrayList<>(usersactivity));
    }

    @GetMapping("/activities/user")
    public List<ActivityDTO> getUsersActivity() {
        List<Activity> usersactivity = manageUserService.getUsersActivity();
        return activityMapper.mapToDto(new ArrayList<>(usersactivity));
    }

    @GetMapping("/outsideuser/{activityName}")
    public List<OutsideUserDTO> getOutsideUsers(@PathVariable String activityName) {
        List<OutsideUser> outsideUserList = manageUserService.getOutsideUsers(activityName);
        return outsideUserMapper.mapToDto(new ArrayList<>(outsideUserList));
    }

    @PostMapping("/activities/{trIdNo}/{activityName}")
    public ActivityDTO addOutsideUsertoActivity(@PathVariable String trIdNo,@PathVariable String activityName, @RequestBody @Valid ActivityDTO activityDTO) {
        Activity outsideUserActivity = manageUserService.addOutsideUsertoActivity(trIdNo,activityName,activityMapper.mapToEntity(activityDTO));
        return activityMapper.mapToDto(outsideUserActivity);
    }

    @GetMapping("/{username}/activities")
    public List<ActivityDTO> getUsersActivity(@PathVariable String username) {
        List<Activity> usersactivity = manageUserService.getUsersActivity(username);
        return activityMapper.mapToDto(new ArrayList<>(usersactivity));
    }

    @PostMapping("/{username}/activities")
    public ActivityDTO addActivityToUsers(@PathVariable String username, @RequestBody @Valid ActivityDTO activityDTO) {
        Activity addedactivity = manageUserService.addActivityToUsers(username, activityMapper.mapToEntity(activityDTO));
        return activityMapper.mapToDto(addedactivity);
    }

    @PutMapping("/{username}/activities/{activityName}")
    public ActivityDTO updateActivity(@PathVariable String username,@PathVariable String activityName, @RequestBody @Valid ActivityDTO activityDTO) {
        Activity activities = activityMapper.mapToEntity(activityDTO);
        Activity updatedactivity= manageUserService.updateActivity(username , activityName, activities);
        return activityMapper.mapToDto(updatedactivity);
    }

    @DeleteMapping("/{username}/activities/{activityName}")
    public void deleteActivity(@PathVariable String username, @PathVariable String activityName) {
        manageUserService.deleteActivity(activityName);
    }

}

