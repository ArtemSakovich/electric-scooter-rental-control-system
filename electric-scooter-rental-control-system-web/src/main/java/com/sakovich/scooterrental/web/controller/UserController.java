package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IUserService;
import com.sakovich.scooterrental.model.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping(value = "/manager/users")
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/user/users/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping(value = "/admin/user")
    public UserDto updateWithRole(@RequestBody UserDto dto) {
        return userService.updateWithRole(dto);
    }

    @PutMapping(value = "/user/user")
    public UserDto updateWithoutRole(@RequestBody UserDto dto) {
        return userService.updateWithoutRole(dto);
    }

    @DeleteMapping(value = "/admin/user/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}


