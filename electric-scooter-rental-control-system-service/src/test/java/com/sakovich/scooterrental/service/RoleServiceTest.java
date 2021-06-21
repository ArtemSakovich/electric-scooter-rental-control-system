package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.IRoleMapper;
import com.sakovich.scooterrental.dao.IRoleDao;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    IRoleDao roleDao;

    @InjectMocks
    RoleService roleService;

    @Mock
    IRoleMapper roleMapper;

    Role role;

    @BeforeEach
    void setUp() {
        role = Role.builder().id(1L).name("Some name...").build();
    }

    @Test
    void getAll() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().id(1L).name("ADMIN").build());
        roles.add(Role.builder().id(2L).name("USER").build());
        roles.add(Role.builder().id(3L).name("MANAGER").build());

        given(roleDao.findAll()).willReturn(roles);

        int receivedSize = roleService.getAll().size();

        assertEquals(roles.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(roleDao.findById(1L)).willReturn(java.util.Optional.of(new Role()));
        given(roleMapper.toDto(any(Role.class))).willReturn(new RoleDto());
        RoleDto returnedRole = (roleService.getById(1L));
        assertNotNull(returnedRole);
    }

    @Test
    void save() {
        given(roleDao.save(any(Role.class))).willReturn(new Role());
        given(roleMapper.toDto(any(Role.class))).willReturn(new RoleDto());
        given(roleMapper.toEntity(any(RoleDto.class))).willReturn(new Role());
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ROLE_NAME");
        RoleDto savedRole = roleService.addRole(roleDto);
        assertNotNull(savedRole);
    }

    @Test
    void delete() {
        given(roleDao.findById(1L)).willReturn(java.util.Optional.of(new Role()));
        roleService.delete(1L);
        then(roleDao).should(times(1)).delete(any(Role.class));
        verifyNoMoreInteractions(roleDao);
    }
}