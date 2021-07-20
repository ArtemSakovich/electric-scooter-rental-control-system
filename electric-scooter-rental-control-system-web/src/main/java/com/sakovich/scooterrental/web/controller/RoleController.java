package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IRoleService;
import com.sakovich.scooterrental.model.dto.RoleDto;
import com.sakovich.scooterrental.web.utils.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Logging
public class RoleController {

    private final IRoleService roleService;

    @PostMapping(value = "/admin/role")
    public RoleDto addRole(@RequestBody RoleDto dto) {
        return roleService.addRole(dto);
    }

    @GetMapping(value = "/admin/roles")
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @GetMapping(value = "/admin/roles/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PutMapping(value = "/admin/role")
    public RoleDto update(@RequestBody RoleDto dto) {
        return roleService.update(dto);
    }

    @DeleteMapping(value = "/admin/role/{id}")
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}



