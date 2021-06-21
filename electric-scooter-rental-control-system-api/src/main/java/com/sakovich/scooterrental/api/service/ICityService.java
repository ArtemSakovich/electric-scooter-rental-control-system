package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.CityDto;

import java.util.List;

public interface ICityService {

    CityDto addCity(CityDto dto);

    List<CityDto> getAll();

    CityDto getById(Long id);

    CityDto update(CityDto dto);

    void delete(Long id);
}
