package com.yte.project.homework.manageusers.entity;

import com.yte.project.homework.common.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "ACTIVITY_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends BaseEntity {

    @ManyToMany(mappedBy = "activities",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Users> usersActivities;

    @ManyToMany(mappedBy = "outsideActivities",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<OutsideUser> outsideUserActivities;

    @Column(name = "ACTIVITY_NAME",unique = true)
    private String activityName;
    @Column(name = "START_DATE")
    @FutureOrPresent
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    @Future
    private LocalDateTime endDate;
    @Column(name = "CAPACITY")
    private int capacity;
    @Column(name = "LATITUDE")
    private float latitude;
    @Column(name = "LONGITUDE")
    private float longitude;
    @Column(name="REGISTERED")
    private int registered=0;

    public boolean capacityCheck(){
        if(outsideUserActivities==null)
            return capacity>=1;
        else
            return outsideUserActivities.size()<=capacity;
    }

    public boolean DateValidation(){
        return endDate.isAfter(startDate);
    }

    public boolean isStartDatePast() { return LocalDateTime.now().isAfter(startDate); }
}
