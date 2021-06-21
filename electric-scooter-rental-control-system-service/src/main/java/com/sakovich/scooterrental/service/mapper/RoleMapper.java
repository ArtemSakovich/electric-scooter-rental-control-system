package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IRoleMapper;
import com.sakovich.scooterrental.model.Role;
import com.sakovich.scooterrental.model.dto.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleMapper implements IRoleMapper {

    private final ModelMapper mapper;

    @Autowired
    public RoleMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Role toEntity(RoleDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, Role.class);
    }

    @Override
    public RoleDto toDto(Role entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, RoleDto.class);
    }
}
