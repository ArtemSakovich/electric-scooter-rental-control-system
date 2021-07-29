package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IRoleMapper;
import com.sakovich.scooterrental.api.service.IRoleService;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.dto.RoleDto;
import com.sakovich.scooterrental.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final IRoleRepository roleDao;
    private final IRoleMapper roleMapper;

    @Override
    public RoleDto addRole(RoleDto dto) {
        if (isDtoValid(dto)) {
            Role entity = roleMapper.toEntity(dto);
            return roleMapper.toDto(roleDao.save(entity));
        }
        return roleMapper.toDto(roleMapper.toEntity(dto));
    }

    @Override
    public List<RoleDto> getAll() {
        return roleDao.findAll().stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto getById(Long id) {
        return roleMapper.toDto(getRoleByIdHandler(id));
    }

    @Override
    public RoleDto update(RoleDto dto) {
        if (isDtoValid(dto)) {
            Role roleToUpdate = getRoleByIdHandler(dto.getId());
            roleToUpdate.setName(dto.getName());
            roleDao.save(roleToUpdate);
        }
        return roleMapper.toDto(roleDao.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(getRoleByIdHandler(id));
    }

    private Role getRoleByIdHandler(Long id) {
        Optional<Role> optionalRole = roleDao.findById(id);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        } else {
            log.error("Error when trying to get role by id");
            throw new OperationCancelledException("Role was not found!");
        }
    }

    private boolean isDtoValid(RoleDto dto) {
        if (dto.getName() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update role");
            throw new OperationCancelledException("Invalid role parameters!");
        }
    }
}
