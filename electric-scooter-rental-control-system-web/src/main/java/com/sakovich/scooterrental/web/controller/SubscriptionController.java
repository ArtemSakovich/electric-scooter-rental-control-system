package com.sakovich.scooterrental.web.controller;

import com.sakovich.scooterrental.api.service.ISubscriptionService;
import com.sakovich.scooterrental.model.dto.SubscriptionDto;
import com.sakovich.scooterrental.web.utils.annotation.Logging;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Logging
public class SubscriptionController {

    private final ISubscriptionService subscriptionService;

    @PostMapping(value = "/manager/subscription")
    public SubscriptionDto addSubscription(@RequestBody SubscriptionDto dto) {
        return subscriptionService.addSubscription(dto);
    }

    @GetMapping(value = "/manager/subscriptions")
    public List<SubscriptionDto> getAll() {
        return subscriptionService.getAll();
    }

    @GetMapping(value = "/user/user-subscriptions/{id}")
    public List<SubscriptionDto> getAllByUserId(@PathVariable Long id) {
        return subscriptionService.getAllByUserId(id);
    }

    @GetMapping(value = "/user/subscriptions/{id}")
    public SubscriptionDto getById(@PathVariable Long id) {
        return subscriptionService.getById(id);
    }

    @PutMapping(value = "/manager/subscription")
    public SubscriptionDto update(@RequestBody SubscriptionDto dto) {
        return subscriptionService.update(dto);
    }

    @DeleteMapping(value = "/manager/subscription/{id}")
    public void delete(@PathVariable Long id) {
        subscriptionService.delete(id);
    }
}


