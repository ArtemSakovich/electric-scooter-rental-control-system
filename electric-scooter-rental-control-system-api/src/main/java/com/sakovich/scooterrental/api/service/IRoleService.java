package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.RoleDto;

import java.util.List;

public interface IRoleService {

    RoleDto addRole(RoleDto dto);

    List<RoleDto> getAll();

    RoleDto getById(Long id);

    RoleDto update(RoleDto dto);

    void delete(Long id);
}
