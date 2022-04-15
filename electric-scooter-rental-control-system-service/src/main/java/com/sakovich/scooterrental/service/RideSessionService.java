package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IRideSessionMapper;
import com.sakovich.scooterrental.api.service.IRideSessionService;
import com.sakovich.scooterrental.model.RideSession;
import com.sakovich.scooterrental.model.Scooter;
import com.sakovich.scooterrental.model.Subscription;
import com.sakovich.scooterrental.model.User;
import com.sakovich.scooterrental.model.dto.RideSessionDto;
import com.sakovich.scooterrental.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RideSessionService implements IRideSessionService {

    private final IRideSessionRepository rideSessionDao;
    private final IScooterRepository scooterDao;
    private final ISubscriptionRepository subscriptionDao;
    private final IUserRepository userDao;
    private final IScooterRentalPointRepository scooterRentalPointDao;
    private final IRideSessionMapper rideSessionMapper;

    @Override
    public List<RideSessionDto> getAll() {
        return rideSessionDao.findAll().stream()
                .map(rideSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RideSessionDto getById(Long id) {
        return rideSessionMapper.toDto(getRideSessionByIdHandler(id));
    }

    @Override
    public RideSessionDto update(RideSessionDto dto) {
        if (isDtoValid(dto)) {
            RideSession rideSessionToUpdate = getRideSessionByIdHandler(dto.getId());
            rideSessionToUpdate.setPrice(dto.getPrice());
            rideSessionToUpdate.setRideHours(dto.getRideHours());
            rideSessionToUpdate.setScooter(scooterDao.getById(dto.getScooterId()));
            rideSessionToUpdate.setUser(userDao.getById(dto.getUserId()));
            rideSessionDao.save(rideSessionToUpdate);
        }
        return rideSessionMapper.toDto(rideSessionDao.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        rideSessionDao.delete(getRideSessionByIdHandler(id));
    }

    @Override
    public void orderRide(RideSessionDto dto) {
        User user = userDao.getById(dto.getUserId());
        Scooter scooter = scooterDao.getById(dto.getScooterId());
        Double priceForRideWithoutDiscount = (double) (dto.getRideHours() * scooter.getPricePerHour());
        Double currentPriceToPay = priceForRideWithoutDiscount;
        List<Subscription> activeSortedSubscriptions = user.getActiveSubscriptions().stream()
                .sorted(Comparator.comparing(Subscription::getBalance))
                .collect(Collectors.toList());
        for (Subscription subscription : activeSortedSubscriptions) {
            if (currentPriceToPay > 0) {
                currentPriceToPay = useSubscriptionForPay(subscription, currentPriceToPay);
            }
        }
        if (currentPriceToPay > 0) {
            throw new OperationCancelledException("Unfortunately, you don't have enough money on your balance");
        } else {
            RideSession rideSession = new RideSession(user, scooter, Timestamp.valueOf(LocalDateTime.now()), dto.getRideHours(), priceForRideWithoutDiscount);
            scooter.setScooterRentalPoint(scooterRentalPointDao.getById(dto.getReturnScooterRentalPointId()));
            scooter.setPassedHours(scooter.getPassedHours() + dto.getRideHours());
            scooterDao.save(scooter);
            rideSessionDao.save(rideSession);
        }
    }

    @Override
    public List<RideSessionDto> getAllByUserId(Long id) {
        return rideSessionDao.getByUserId(id).stream()
                .map(rideSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RideSessionDto> getALLByScooterId(Long id) {
        return rideSessionDao.getByScooterId(id).stream()
                .map(rideSessionMapper::toDto)
                .collect(Collectors.toList());
    }

    private Double useSubscriptionForPay(Subscription activeSubscription, Double currentPriceToPay) {
        Double ableToPayByThisSubscription = activeSubscription.getBalance() * (1 + (activeSubscription.getDiscountPercentage() / 100));
        if (currentPriceToPay > ableToPayByThisSubscription) {
            currentPriceToPay -= ableToPayByThisSubscription;
            activeSubscription.setBalance(0.0);
        } else {
            activeSubscription.setBalance(activeSubscription.getBalance() - activeSubscription.getCoefficient() * currentPriceToPay);
            currentPriceToPay = 0.0;
        }
        subscriptionDao.save(activeSubscription);
        return currentPriceToPay;
    }

    private RideSession getRideSessionByIdHandler(Long id) {
        Optional<RideSession> optionalRideSession = rideSessionDao.findById(id);
        if (optionalRideSession.isPresent()) {
            return optionalRideSession.get();
        } else {
            log.error("Error when trying to get ride session by id");
            throw new OperationCancelledException("Ride session was not found!");
        }
    }

    private boolean isDtoValid(RideSessionDto dto) {
        if (dto.getRideHours() != null && dto.getPrice() != null && dto.getReturnScooterRentalPointId() != null
                && dto.getUserId() != null) {
            return true;
        } else {
            log.error("Error when trying to order ride or update ride session");
            throw new OperationCancelledException("Invalid ride session parameters!");
        }
    }
}
