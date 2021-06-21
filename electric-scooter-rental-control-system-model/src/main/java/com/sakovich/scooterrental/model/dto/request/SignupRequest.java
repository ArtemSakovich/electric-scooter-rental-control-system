package com.sakovich.scooterrental.model.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private Integer age;
}
