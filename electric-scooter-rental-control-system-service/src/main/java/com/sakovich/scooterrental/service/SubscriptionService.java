package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.ISubscriptionMapper;
import com.sakovich.scooterrental.api.service.ISubscriptionService;
import com.sakovich.scooterrental.model.Subscription;
import com.sakovich.scooterrental.model.dto.SubscriptionDto;
import com.sakovich.scooterrental.repository.ISubscriptionRepository;
import com.sakovich.scooterrental.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionDao;
    private final ISubscriptionMapper subscriptionMapper;
    private final IUserRepository userDao;

    @Override
    public SubscriptionDto addSubscription(SubscriptionDto dto) {
        if (isDtoValid(dto)) {
            Subscription entity = subscriptionMapper.toEntity(dto);
            entity.setCreatedOn(Timestamp.valueOf(LocalDateTime.now()));
            return subscriptionMapper.toDto(subscriptionDao.save(entity));
        }
        return subscriptionMapper.toDto(subscriptionMapper.toEntity(dto));
    }

    @Override
    public List<SubscriptionDto> getAll() {
        return subscriptionDao.findAll().stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionDto getById(Long id) {
        return subscriptionMapper.toDto(getSubscriptionByIdHandler(id));
    }

    @Override
    public SubscriptionDto update(SubscriptionDto dto) {
        if (isDtoValid(dto)) {
            Subscription subscriptionToUpdate = getSubscriptionByIdHandler(dto.getId());
            subscriptionToUpdate.setUser(userDao.getById(dto.getUserId()));
            subscriptionToUpdate.setPrice(dto.getPrice());
            subscriptionToUpdate.setDiscountPercentage(dto.getDiscountPercentage());
            subscriptionToUpdate.setBalance(dto.getBalance());
            subscriptionDao.save(subscriptionToUpdate);
        }
        return subscriptionMapper.toDto(subscriptionDao.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        subscriptionDao.delete(getSubscriptionByIdHandler(id));
    }

    @Override
    public List<SubscriptionDto> getAllByUserId(Long id) {
        return subscriptionDao.findAllByUserId(id).stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    private Subscription getSubscriptionByIdHandler(Long id) {
        Optional<Subscription> optionalSubscription = subscriptionDao.findById(id);
        if (optionalSubscription.isPresent()) {
            return optionalSubscription.get();
        } else {
            log.error("Error when trying to get subscription by id");
            throw new OperationCancelledException("Subscription was not found!");
        }
    }

    private boolean isDtoValid(SubscriptionDto dto) {
        if (dto.getBalance() != null && dto.getPrice() != null && dto.getUserId() != null
                && dto.getDiscountPercentage() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update subscription");
            throw new OperationCancelledException("Invalid subscription parameters!");
        }
    }
}
