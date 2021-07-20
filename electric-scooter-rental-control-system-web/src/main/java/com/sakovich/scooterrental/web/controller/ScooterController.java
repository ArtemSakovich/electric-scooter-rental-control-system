package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IScooterService;
import com.sakovich.scooterrental.model.dto.ScooterDto;
import com.sakovich.scooterrental.web.utils.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Logging
public class ScooterController {

    private final IScooterService scooterService;

    @PostMapping(value = "/manager/scooter")
    public ScooterDto addScooter(@RequestBody ScooterDto dto) {
        return scooterService.addScooter(dto);
    }

    @GetMapping(value = "/manager/scooters")
    public List<ScooterDto> getAll() {
        return scooterService.getAll();
    }

    @GetMapping(value = "/user/scooters/{id}")
    public ScooterDto getById(@PathVariable Long id) {
        return scooterService.getById(id);
    }

    @PutMapping(value = "/manager/scooter")
    public ScooterDto update(@RequestBody ScooterDto dto) {
        return scooterService.update(dto);
    }

    @DeleteMapping(value = "/manager/scooter/{id}")
    public void delete(@PathVariable Long id) {
        scooterService.delete(id);
    }

    @GetMapping(value = "/user/rental-point/available-scooters/{id}")
    public List<ScooterDto> getAvailableScootersByRentalPointId(@PathVariable Long id) {
        return scooterService.getAvailableScootersByRentalPointId(id);
    }

    @GetMapping(value = "/user/rental-point/scooters/{id}")
    public List<ScooterDto> getAllByRentalPointId(@PathVariable Long id) {
        return scooterService.getAllByRentalPointId(id);
    }

}


