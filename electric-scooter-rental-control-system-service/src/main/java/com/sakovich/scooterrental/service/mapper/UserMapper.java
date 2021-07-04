package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IUserMapper;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.UserDto;
import com.sakovich.scooterrental.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper implements IUserMapper {

    private final ModelMapper mapper;
    private final IRoleRepository roleDao;

    @PostConstruct
    public void setupMapper() {
        TypeMap<User, UserDto> typeMap = mapper.getTypeMap(User.class, UserDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(User.class, UserDto.class)
                    .addMappings(m -> m.skip(UserDto::setRoleId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(UserDto.class, User.class)
                    .addMappings(m -> m.skip(User::setRole)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(User source, UserDto destination) {
        destination.setRoleId(getRoleId(source));
    }

    private Long getRoleId(User source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getRole().getId();
    }

    void mapSpecificFields(UserDto source, User destination) {
        destination.setRole(roleDao.findById(source.getRoleId()).orElse(null));
    }

    Converter<User, UserDto> toDtoConverter() {
        return context -> {
            User source = context.getSource();
            UserDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<UserDto, User> toEntityConverter() {
        return context -> {
            UserDto source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    @Override
    public User toEntity(UserDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, User.class);
    }

    @Override
    public UserDto toDto(User entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, UserDto.class);
    }
}
