package com.yte.project.homework.manageusers.entity;

import com.yte.project.homework.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "AUTHORITY_SEQ")
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority{

    @Column(name = "USER_AUTHORITY")
    private String authority;
}
