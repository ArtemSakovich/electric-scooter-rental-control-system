package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IScooterRentalPointService;
import com.sakovich.scooterrental.model.dto.ScooterRentalPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScooterRentalPointController {

    private final IScooterRentalPointService scooterRentalPointService;

    @PostMapping(value = "/manager/scooter-rental-point")
    public ScooterRentalPointDto addScooterRentalPoint(@RequestBody ScooterRentalPointDto dto) {
        return scooterRentalPointService.addRentalPoint(dto);
    }

    @GetMapping(value = "/user/rental-points")
    public List<ScooterRentalPointDto> getAll() {
        return scooterRentalPointService.getAll();
    }

    @GetMapping(value = "/user/rental-points/{id}")
    public ScooterRentalPointDto getById(@PathVariable Long id) {
        return scooterRentalPointService.getById(id);
    }

    @PutMapping(value = "/manager/scooter-rental-point")
    public ScooterRentalPointDto update(@RequestBody ScooterRentalPointDto dto) {
        return scooterRentalPointService.update(dto);
    }

    @DeleteMapping(value = "/manager/scooter-rental-point/{id}")
    public void delete(@PathVariable Long id) {
        scooterRentalPointService.delete(id);
    }

    @GetMapping(value = "/scooter-rental-points/{cityId}")
    public List<ScooterRentalPointDto> getAllScooterRentalPointsByCity(@PathVariable Long cityId) {
        return scooterRentalPointService.getAllScooterRentalPointsByCity(cityId);
    }

    @GetMapping(value = "/manager/scooter/scooter-rental-point/{scooterId}")
    public ScooterRentalPointDto getScooterRentalPointByScooterId(@PathVariable Long scooterId) {
        return scooterRentalPointService.getScooterRentalPointByScooterId(scooterId);
    }

}


