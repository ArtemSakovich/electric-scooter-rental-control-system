package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.ScooterModelDto;

import java.util.List;

public interface IScooterModelService {

    ScooterModelDto addScooterModel(ScooterModelDto dto);

    List<ScooterModelDto> getAll();

    ScooterModelDto getById(Long id);

    ScooterModelDto update(ScooterModelDto dto);

    void delete(Long id);
}
