package com.yte.project.homework.manageusers.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ActivityDTO {

    public final String activityName;

    public final LocalDateTime startDate;

    public final LocalDateTime endDate;

    public final int capacity;

    public final float latitude;

    public final float longitude;

    public final int registered;

    public ActivityDTO(@JsonProperty("activityName") String activityName,
                       @JsonProperty("startDate") LocalDateTime startDate,
                       @JsonProperty("endDate") LocalDateTime endDate,
                       @JsonProperty("capacity") int capacity,
                       @JsonProperty("latitude") float latitude,
                       @JsonProperty("longitude") float longitude,
                       @JsonProperty("registered") int registered) {
        this.activityName = activityName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.registered = registered;
    }
}
