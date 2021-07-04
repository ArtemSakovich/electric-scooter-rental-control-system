package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IScooterRentalPointMapper;
import com.sakovich.scooterrental.model.ScooterRentalPoint;
import com.sakovich.scooterrental.model.dto.ScooterRentalPointDto;
import com.sakovich.scooterrental.repository.ICityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScooterRentalPointMapper implements IScooterRentalPointMapper {

    private final ModelMapper mapper;
    private final ICityRepository cityDao;

    @PostConstruct
    public void setupMapper() {
        TypeMap<ScooterRentalPoint, ScooterRentalPointDto> typeMap = mapper.getTypeMap(ScooterRentalPoint.class, ScooterRentalPointDto.class);
        if (typeMap == null) {
            mapper.createTypeMap(ScooterRentalPoint.class, ScooterRentalPointDto.class)
                    .addMappings(m -> m.skip(ScooterRentalPointDto::setCityId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(ScooterRentalPointDto.class, ScooterRentalPoint.class)
                    .addMappings(m -> m.skip(ScooterRentalPoint::setCity)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(ScooterRentalPoint source, ScooterRentalPointDto destination) {
        destination.setCityId(getCityId(source));
    }

    private Long getCityId(ScooterRentalPoint source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getCity().getId();
    }

    void mapSpecificFields(ScooterRentalPointDto source, ScooterRentalPoint destination) {
        destination.setCity(cityDao.findById(source.getCityId()).orElse(null));
    }

    Converter<ScooterRentalPoint, ScooterRentalPointDto> toDtoConverter() {
        return context -> {
            ScooterRentalPoint source = context.getSource();
            ScooterRentalPointDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<ScooterRentalPointDto, ScooterRentalPoint> toEntityConverter() {
        return context -> {
            ScooterRentalPointDto source = context.getSource();
            ScooterRentalPoint destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    @Override
    public ScooterRentalPoint toEntity(ScooterRentalPointDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, ScooterRentalPoint.class);
    }

    @Override
    public ScooterRentalPointDto toDto(ScooterRentalPoint entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, ScooterRentalPointDto.class);
    }
}
