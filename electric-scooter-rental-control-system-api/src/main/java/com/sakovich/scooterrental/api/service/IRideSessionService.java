package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.RideSessionDto;

import java.util.List;

public interface IRideSessionService {

    List<RideSessionDto> getAll();

    RideSessionDto getById(Long id);

    RideSessionDto update(RideSessionDto dto);

    void delete(Long id);

    void orderRide(RideSessionDto dto);

    List<RideSessionDto> getAllByUserId(Long id);

    List<RideSessionDto> getALLByScooterId(Long id);
}
