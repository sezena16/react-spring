package com.yte.project.homework.manageusers.entity;

import com.yte.project.homework.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@SequenceGenerator(name = "idgen", sequenceName = "OUTSIDE_USER_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class OutsideUser extends BaseEntity{
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "TR_ID_NO", unique = true)
    private String trIdNo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "OUTSIDE_USER_ACTIVITIES",
            joinColumns = @JoinColumn(name = "OUTSIDE_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID"))
    private List<Activity> outsideActivities;
}
