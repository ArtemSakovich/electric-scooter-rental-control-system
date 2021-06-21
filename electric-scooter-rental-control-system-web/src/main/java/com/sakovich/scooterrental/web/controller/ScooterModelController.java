package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IScooterModelService;
import com.sakovich.scooterrental.model.dto.ScooterModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class ScooterModelController {

    private final IScooterModelService scooterModelService;

    @Autowired
    public ScooterModelController(IScooterModelService scooterModelService) {
        this.scooterModelService = scooterModelService;
    }

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


