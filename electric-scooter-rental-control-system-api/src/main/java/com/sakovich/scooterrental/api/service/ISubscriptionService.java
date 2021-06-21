package com.sakovich.scooterrental.api.service;

import com.sakovich.scooterrental.model.dto.SubscriptionDto;

import java.util.List;

public interface ISubscriptionService {

    SubscriptionDto addSubscription(SubscriptionDto dto);

    List<SubscriptionDto> getAll();

    SubscriptionDto getById(Long id);

    SubscriptionDto update(SubscriptionDto dto);

    void delete(Long id);

    List<SubscriptionDto> getAllByUserId(Long id);
}
