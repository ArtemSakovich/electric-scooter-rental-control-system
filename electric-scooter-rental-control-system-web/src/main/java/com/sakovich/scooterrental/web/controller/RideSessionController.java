package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.IRideSessionService;
import com.sakovich.scooterrental.model.dto.RideSessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
public class RideSessionController {

    private final IRideSessionService rideSessionService;

    @Autowired
    public RideSessionController(IRideSessionService rideSessionService) {
        this.rideSessionService = rideSessionService;
    }

    @GetMapping(value = "/manager/ride-sessions")
    public List<RideSessionDto> getAll() {
        return rideSessionService.getAll();
    }

    @GetMapping(value = "/manager/ride-sessions/{id}")
    public RideSessionDto getById(@PathVariable Long id) {
        return rideSessionService.getById(id);
    }

    @PutMapping(value = "/manager/ride-session")
    public RideSessionDto update(@RequestBody RideSessionDto dto) {
        return rideSessionService.update(dto);
    }

    @DeleteMapping(value = "/manager/ride-session/{id}")
    public void delete(@PathVariable Long id) {
        rideSessionService.delete(id);
    }

    @GetMapping(value = "/user/ride-sessions/{id}")
    public List<RideSessionDto> getALLByUserId(@PathVariable Long id) {
        return rideSessionService.getAllByUserId(id);
    }

    @GetMapping(value = "/manager/scooter/ride-sessions/{id}")
    public List<RideSessionDto> getALLByScooterId(@PathVariable Long id) {
        return rideSessionService.getALLByScooterId(id);
    }

    @PostMapping(value = "/user/order-ride")
    public void orderRide(@RequestBody RideSessionDto dto) {
        rideSessionService.orderRide(dto);
    }

}



