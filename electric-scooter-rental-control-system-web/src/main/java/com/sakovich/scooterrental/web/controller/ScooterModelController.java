package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IScooterModelService;
import com.sakovich.scooterrental.model.dto.ScooterModelDto;
import com.sakovich.scooterrental.web.utils.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Logging
public class ScooterModelController {

    private final IScooterModelService scooterModelService;

    @PostMapping(value = "/manager/scooter-model")
    public ScooterModelDto addScooterModel(@RequestBody ScooterModelDto dto) {
        return scooterModelService.addScooterModel(dto);
    }

    @GetMapping(value = "/manager/scooter-models")
    public List<ScooterModelDto> getAll() {
        return scooterModelService.getAll();
    }

    @GetMapping(value = "/user/scooter-models/{id}")
    public ScooterModelDto getById(@PathVariable Long id) {
        return scooterModelService.getById(id);
    }

    @PutMapping(value = "/manager/scooter-model")
    public ScooterModelDto update(@RequestBody ScooterModelDto dto) {
        return scooterModelService.update(dto);
    }

    @DeleteMapping(value = "/manager/scooter-model/{id}")
    public void delete(@PathVariable Long id) {
        scooterModelService.delete(id);
    }
}


