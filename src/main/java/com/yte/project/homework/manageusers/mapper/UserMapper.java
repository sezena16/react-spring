package com.yte.project.homework.manageusers.mapper;

import com.yte.project.homework.manageusers.DTO.UsersDTO;
import com.yte.project.homework.manageusers.entity.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersDTO mapToDto(Users users);

    Users mapToEntity(UsersDTO usersDTO);

    List<UsersDTO> mapToDto(List<Users> usersList);

    List<Users> mapToEntity(List<UsersDTO> usersDTOList);
}


