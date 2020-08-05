package com.yte.project.homework.manageusers.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorityDTO {

    public final String authority;

    public AuthorityDTO(@JsonProperty("authority") String authority) {
        this.authority = authority;
    }
}
