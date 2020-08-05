package com.yte.project.homework.manageusers.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yte.project.homework.manageusers.validation.TrIdNo;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@Builder
public class OutsideUserDTO {

    public final String name;

    public final String surname;

    @Size(min = 11, max = 11, message = "TR Id number must be 11 characters long!")
    @TrIdNo(message = "TR Id number must be valid!")
    public final String trIdNo;

    @JsonCreator
    public OutsideUserDTO(@JsonProperty("name") String name,
                          @JsonProperty("surname") String surname,
                          @JsonProperty("trIdNo") String trIdNo) {
        this.name = name;
        this.surname = surname;
        this.trIdNo=trIdNo;
    }
}

