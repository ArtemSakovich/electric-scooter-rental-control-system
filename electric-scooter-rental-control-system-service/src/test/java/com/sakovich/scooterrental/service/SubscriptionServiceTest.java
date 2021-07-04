package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.ISubscriptionMapper;
import com.sakovich.scooterrental.repository.ISubscriptionRepository;
import com.sakovich.scooterrental.model.Subscription;
import com.sakovich.scooterrental.model.dto.SubscriptionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    ISubscriptionRepository subscriptionDao;

    @InjectMocks
    SubscriptionService subscriptionService;

    @Mock
    ISubscriptionMapper subscriptionMapper;

    Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = Subscription.builder().id(1L).balance(300.0d).price(15.5d).build();
    }

    @Test
    void getAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(Subscription.builder().id(1L).balance(300.0d).price(15.5d).build());
        subscriptions.add(Subscription.builder().id(2L).balance(310.0d).price(20.5d).build());
        subscriptions.add(Subscription.builder().id(3L).balance(320.0d).price(25.5d).build());

        given(subscriptionDao.findAll()).willReturn(subscriptions);

        int receivedSize = subscriptionService.getAll().size();

        assertEquals(subscriptions.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(subscriptionDao.findById(1L)).willReturn(java.util.Optional.of(new Subscription()));
        given(subscriptionMapper.toDto(any(Subscription.class))).willReturn(new SubscriptionDto());
        SubscriptionDto returnedSubscription = (subscriptionService.getById(1L));
        assertNotNull(returnedSubscription);
    }

    @Test
    void save() {
        given(subscriptionDao.save(any(Subscription.class))).willReturn(new Subscription());
        given(subscriptionMapper.toDto(any(Subscription.class))).willReturn(new SubscriptionDto());
        given(subscriptionMapper.toEntity(any(SubscriptionDto.class))).willReturn(new Subscription());
        SubscriptionDto dto = new SubscriptionDto();
        dto.setUserId(1L);
        dto.setBalance(500.0);
        dto.setPrice(1500.0);
        dto.setDiscountPercentage(15.0f);
        SubscriptionDto savedSubscription = subscriptionService.addSubscription(dto);
        assertNotNull(savedSubscription);
    }

    @Test
    void delete() {
        given(subscriptionDao.findById(1L)).willReturn(java.util.Optional.of(new Subscription()));
        subscriptionService.delete(1L);
        then(subscriptionDao).should(times(1)).delete(any(Subscription.class));
        verifyNoMoreInteractions(subscriptionDao);
    }

    @Test
    void getAllByUserId() {
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(Subscription.builder().id(1L).balance(300.0d).price(15.5d).build());
        subscriptions.add(Subscription.builder().id(2L).balance(310.0d).price(20.5d).build());
        subscriptions.add(Subscription.builder().id(3L).balance(320.0d).price(25.5d).build());

        given(subscriptionDao.findAllByUserId(1L)).willReturn(subscriptions);

        int receivedSize = subscriptionService.getAllByUserId(1L).size();

        assertEquals(subscriptions.size(), receivedSize);
    }
}