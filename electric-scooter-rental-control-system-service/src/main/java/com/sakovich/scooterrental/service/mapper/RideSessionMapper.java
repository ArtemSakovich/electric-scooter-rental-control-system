package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IRideSessionMapper;
import com.sakovich.scooterrental.model.RideSession;
import com.sakovich.scooterrental.model.dto.RideSessionDto;
import com.sakovich.scooterrental.repository.IScooterRepository;
import com.sakovich.scooterrental.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RideSessionMapper implements IRideSessionMapper {

    private final ModelMapper mapper;
    private final IScooterRepository scooterDao;
    private final IUserRepository userDao;

    @PostConstruct
    public void setupMapper() {
        TypeMap<RideSession, RideSessionDto> typeMap = mapper.getTypeMap(RideSession.class, RideSessionDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(RideSession.class, RideSessionDto.class)
                    .addMappings(m -> m.skip(RideSessionDto::setUserId)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(RideSessionDto::setScooterId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(RideSessionDto.class, RideSession.class)
                    .addMappings(m -> m.skip(RideSession::setUser)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(RideSession::setScooter)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(RideSession source, RideSessionDto destination) {
        destination.setUserId(getUserId(source));
        destination.setScooterId(getScooterId(source));
    }

    private Long getUserId(RideSession source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUser().getId();
    }

    private Long getScooterId(RideSession source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getScooter().getId();
    }

    void mapSpecificFields(RideSessionDto source, RideSession destination) {
        destination.setUser(userDao.findById(source.getUserId()).orElse(null));
        destination.setScooter(scooterDao.findById(source.getScooterId()).orElse(null));
    }

    Converter<RideSession, RideSessionDto> toDtoConverter() {
        return context -> {
            RideSession source = context.getSource();
            RideSessionDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<RideSessionDto, RideSession> toEntityConverter() {
        return context -> {
            RideSessionDto source = context.getSource();
            RideSession destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    @Override
    public RideSession toEntity(RideSessionDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, RideSession.class);
    }

    @Override
    public RideSessionDto toDto(RideSession entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, RideSessionDto.class);
    }
}
