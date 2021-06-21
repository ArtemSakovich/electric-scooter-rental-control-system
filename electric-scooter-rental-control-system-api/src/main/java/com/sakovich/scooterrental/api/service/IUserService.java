package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.UserDto;

import java.util.List;

public interface IUserService {

    UserDto getById(Long id);

    List<UserDto> getAll();

    User getUserByEmail(String email);

    UserDto updateWithRole(UserDto dto);

    void delete(Long id);

    UserDto updateWithoutRole(UserDto dto);
}
