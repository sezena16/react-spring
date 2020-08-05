package com.yte.project.homework.manageusers.mapper;

import com.yte.project.homework.manageusers.DTO.ActivityDTO;
import com.yte.project.homework.manageusers.entity.Activity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    ActivityDTO mapToDto(Activity activity);

    Activity mapToEntity(ActivityDTO activityDTO);

    List<ActivityDTO> mapToDto(List<Activity> activityList);

    List<Activity> mapToEntity(List<ActivityDTO> activityDTOList);

}
