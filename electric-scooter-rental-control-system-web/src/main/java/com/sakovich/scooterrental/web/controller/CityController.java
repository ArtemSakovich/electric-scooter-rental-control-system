package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.ICityService;
import com.sakovich.scooterrental.model.dto.CityDto;
import com.sakovich.scooterrental.web.utils.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Logging
public class CityController {

    private final ICityService cityService;

    @PostMapping(value = "/manager/city")
    public CityDto addCity(@RequestBody CityDto dto) {
        return cityService.addCity(dto);
    }

    @GetMapping(value = "/cities")
    public List<CityDto> getAll() {
        return cityService.getAll();
    }

    @GetMapping(value = "/user/cities/{id}")
    public CityDto getById(@PathVariable Long id) {
        return cityService.getById(id);
    }

    @PutMapping(value = "/manager/city")
    public CityDto update(@RequestBody CityDto dto) {
        return cityService.update(dto);
    }

    @DeleteMapping(value = "/manager/city/{id}")
    public void delete(@PathVariable Long id) {
        cityService.delete(id);
    }
}

