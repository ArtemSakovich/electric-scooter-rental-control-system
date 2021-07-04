package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IScooterRentalPointMapper;
import com.sakovich.scooterrental.api.service.IScooterRentalPointService;
import com.sakovich.scooterrental.repository.ICityRepository;
import com.sakovich.scooterrental.repository.IScooterRentalPointRepository;
import com.sakovich.scooterrental.model.ScooterRentalPoint;
import com.sakovich.scooterrental.model.dto.ScooterRentalPointDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ScooterRentalPointService implements IScooterRentalPointService {

    private final IScooterRentalPointRepository scooterRentalPointDao;
    private final IScooterRentalPointMapper scooterRentalPointMapper;
    private final ICityRepository cityDao;

    @Override
    public ScooterRentalPointDto addRentalPoint(ScooterRentalPointDto dto) {
        if (isDtoValid(dto)) {
            ScooterRentalPoint scooterRentalPoint = scooterRentalPointMapper.toEntity(dto);
            scooterRentalPointDao.save(scooterRentalPoint);
        }
        return scooterRentalPointMapper.toDto(scooterRentalPointMapper.toEntity(dto));
    }

    @Override
    public ScooterRentalPointDto getById(Long id) {
        ScooterRentalPointDto dto = scooterRentalPointMapper.toDto(getScooterRentalPointByIdHandler(id));
        return scooterRentalPointMapper.toDto(getScooterRentalPointByIdHandler(id));
    }

    @Override
    public List<ScooterRentalPointDto> getAll() {
        return scooterRentalPointDao.findAll().stream()
                .map(scooterRentalPointMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScooterRentalPointDto update(ScooterRentalPointDto dto) {
        if (isDtoValid(dto)) {
            ScooterRentalPoint rentalPointToUpdate = getScooterRentalPointByIdHandler(dto.getId());
            rentalPointToUpdate.setCity(cityDao.getById(dto.getCityId()));
            rentalPointToUpdate.setStreet(dto.getStreet());
            rentalPointToUpdate.setBuildingNumber(dto.getBuildingNumber());
            scooterRentalPointDao.save(rentalPointToUpdate);
        }
        return scooterRentalPointMapper.toDto(scooterRentalPointDao.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        scooterRentalPointDao.delete(getScooterRentalPointByIdHandler(id));
    }

    @Override
    public List<ScooterRentalPointDto> getAllScooterRentalPointsByCity(Long cityId) {
        return scooterRentalPointDao.findSByCity_Id(cityId).stream()
                .map(scooterRentalPointMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScooterRentalPointDto getScooterRentalPointByScooterId(Long scooterId) {
        return scooterRentalPointMapper.toDto(scooterRentalPointDao.getByScooterId(scooterId));
    }

    private ScooterRentalPoint getScooterRentalPointByIdHandler(Long id) {
        Optional<ScooterRentalPoint> optionalScooterRentalPoint = scooterRentalPointDao.findById(id);
        if (optionalScooterRentalPoint.isPresent()) {
            return optionalScooterRentalPoint.get();
        } else {
            log.error("Error when trying to get scooter rental point by id");
            throw new OperationCancelledException("Scooter rental point was not found!");
        }
    }

    private boolean isDtoValid(ScooterRentalPointDto dto) {
        if (dto.getCityId() != null && dto.getStreet() != null && dto.getBuildingNumber() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update scooter rental point");
            throw new OperationCancelledException("Invalid scooter rental point parameters!");
        }
    }
}