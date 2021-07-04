package com.sakovich.scooterrental.service.mapper;

import com.sakovich.scooterrental.api.mapper.ICityMapper;
import com.sakovich.scooterrental.model.City;
import com.sakovich.scooterrental.model.dto.CityDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CityMapper implements ICityMapper {

    private final ModelMapper mapper;

    @Override
    public City toEntity(CityDto dto) {
        return Objects.isNull(dto) ? null : mapper.map(dto, City.class);
    }

    @Override
    public CityDto toDto(City entity) {
        return Objects.isNull(entity) ? null : mapper.map(entity, CityDto.class);
    }
}
