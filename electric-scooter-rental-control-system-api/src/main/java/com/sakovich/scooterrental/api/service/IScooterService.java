package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.ScooterDto;

import java.util.List;

public interface IScooterService {

    ScooterDto addScooter(ScooterDto dto);

    List<ScooterDto> getAll();

    ScooterDto getById(Long id);

    ScooterDto update(ScooterDto dto);

    void delete(Long id);

    List<ScooterDto> getAllByRentalPointId(Long id);

    List<ScooterDto> getAvailableScootersByRentalPointId(Long id);
}
