package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.IScooterModelMapper;
import com.sakovich.scooterrental.model.ScooterModel;
import com.sakovich.scooterrental.model.dto.ScooterModelDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ScooterModelMapper implements IScooterModelMapper {

    private final ModelMapper mapper;

    @Override
    public ScooterModel toEntity(ScooterModelDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, ScooterModel.class);
    }

    @Override
    public ScooterModelDto toDto(ScooterModel entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, ScooterModelDto.class);
    }
}
