package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.ScooterRentalPointDto;

import java.util.List;

public interface IScooterRentalPointService {

    ScooterRentalPointDto addRentalPoint(ScooterRentalPointDto dto);

    ScooterRentalPointDto getById(Long id);

    List<ScooterRentalPointDto> getAll();

    ScooterRentalPointDto update(ScooterRentalPointDto dto);

    void delete(Long id);

    List<ScooterRentalPointDto> getAllScooterRentalPointsByCity(Long cityId);

    ScooterRentalPointDto getScooterRentalPointByScooterId(Long scooterId);
}
