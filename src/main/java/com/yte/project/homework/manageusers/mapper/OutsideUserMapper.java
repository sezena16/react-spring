package com.yte.project.homework.manageusers.mapper;

import com.yte.project.homework.manageusers.DTO.OutsideUserDTO;
import com.yte.project.homework.manageusers.entity.OutsideUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OutsideUserMapper {

    OutsideUserDTO mapToDto(OutsideUser outsideUser);

    OutsideUser mapToEntity(OutsideUserDTO outsideUserDTO);

    List<OutsideUserDTO> mapToDto(List<OutsideUser> outsideUserList);

    List<OutsideUser> mapToEntity(List<OutsideUserDTO> outsideUserDTOList);
}


