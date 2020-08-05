package com.yte.project.homework.manageusers.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersDTO {

    public final String username;

    public final String password;

    @JsonCreator
    public UsersDTO(@JsonProperty("username") String username,
                      @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }
}
