package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IScooterMapper;
import com.sakovich.scooterrental.dao.IScooterModelDao;
import com.sakovich.scooterrental.dao.IScooterRentalPointDao;
import com.sakovich.scooterrental.model.Scooter;
import com.sakovich.scooterrental.model.dto.ScooterDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class ScooterMapper implements IScooterMapper {

    private final ModelMapper mapper;
    private final IScooterModelDao scooterModelDao;
    private final IScooterRentalPointDao scooterRentalPointDao;

    @Autowired
    public ScooterMapper(ModelMapper mapper, IScooterModelDao scooterModelDao, IScooterRentalPointDao scooterRentalPointDao) {
        this.mapper = mapper;
        this.scooterModelDao = scooterModelDao;
        this.scooterRentalPointDao = scooterRentalPointDao;
    }

    @PostConstruct
    public void setupMapper() {
        TypeMap<Scooter, ScooterDto> typeMap = mapper.getTypeMap(Scooter.class, ScooterDto.class);
        if (typeMap == null) { // if not  already added
            mapper.createTypeMap(Scooter.class, ScooterDto.class)
                    .addMappings(m -> m.skip(ScooterDto::setModelId)).setPostConverter(toDtoConverter())
                    .addMappings(m -> m.skip(ScooterDto::setScooterRentalPointId)).setPostConverter(toDtoConverter());
            mapper.createTypeMap(ScooterDto.class, Scooter.class)
                    .addMappings(m -> m.skip(Scooter::setModel)).setPostConverter(toEntityConverter())
                    .addMappings(m -> m.skip(Scooter::setScooterRentalPoint)).setPostConverter(toEntityConverter());
        }
    }

    void mapSpecificFields(Scooter source, ScooterDto destination) {
        destination.setModelId(getModelId(source));
        destination.setScooterRentalPointId(getScooterRentalPointId(source));
    }

    private Long getScooterRentalPointId(Scooter source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getScooterRentalPoint().getId();
    }

    private Long getModelId(Scooter source) {
        return Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getModel().getId();
    }

    void mapSpecificFields(ScooterDto source, Scooter destination) {
        destination.setModel(scooterModelDao.findById(source.getModelId()).orElse(null));
        destination.setScooterRentalPoint(scooterRentalPointDao.findById(source.getScooterRentalPointId()).orElse(null));
    }

    Converter<Scooter, ScooterDto> toDtoConverter() {
        return context -> {
            Scooter source = context.getSource();
            ScooterDto destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    Converter<ScooterDto, Scooter> toEntityConverter() {
        return context -> {
            ScooterDto source = context.getSource();
            Scooter destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    @Override
    public Scooter toEntity(ScooterDto dto) {
        return Objects.isNull(dto)
                ? null
                : mapper.map(dto, Scooter.class);
    }

    @Override
    public ScooterDto toDto(Scooter entity) {
        return Objects.isNull(entity)
                ? null
                : mapper.map(entity, ScooterDto.class);
    }
}
