package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IScooterMapper;
import com.sakovich.scooterrental.api.service.IScooterService;
import com.sakovich.scooterrental.dao.IScooterDao;
import com.sakovich.scooterrental.dao.IScooterModelDao;
import com.sakovich.scooterrental.dao.IScooterRentalPointDao;
import com.sakovich.scooterrental.model.Scooter;
import com.sakovich.scooterrental.model.dto.ScooterDto;
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
public class ScooterService implements IScooterService {

    private final IScooterDao scooterDao;
    private final IScooterModelDao scooterModelDao;
    private final IScooterRentalPointDao rentalPointDao;
    private final IScooterMapper scooterMapper;

    private static final Logger log = LogManager.getLogger(ScooterService.class);

    @Autowired
    public ScooterService(IScooterDao scooterDao, IScooterModelDao scooterModelDao,
                          IScooterRentalPointDao rentalPointDao, IScooterMapper scooterMapper) {
        this.scooterDao = scooterDao;
        this.scooterModelDao = scooterModelDao;
        this.rentalPointDao = rentalPointDao;
        this.scooterMapper = scooterMapper;
    }

    @Override
    public ScooterDto addScooter(ScooterDto dto) {
        if (isDtoValid(dto)) {
            Scooter entity = scooterMapper.toEntity(dto);
            scooterDao.save(entity);
        }
        return scooterMapper.toDto(scooterMapper.toEntity(dto));
    }

    @Override
    public List<ScooterDto> getAll() {
        return scooterDao.findAll().stream()
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScooterDto getById(Long id) {
        return scooterMapper.toDto(getScooterByIdHandler(id));
    }

    @Override
    public ScooterDto update(ScooterDto dto) {
        if (isDtoValid(dto)) {
            Scooter scooterToUpdate = getScooterByIdHandler(dto.getId());
            scooterToUpdate.setModel(scooterModelDao.getById(dto.getModelId()));
            scooterToUpdate.setScooterRentalPoint(rentalPointDao.getById(dto.getScooterRentalPointId()));
            scooterToUpdate.setPassedHours(dto.getPassedHours());
            scooterToUpdate.setStatus(dto.getStatus());
            scooterToUpdate.setPricePerHour(dto.getPricePerHour());
            scooterDao.save(scooterToUpdate);
        }
        return scooterMapper.toDto(scooterDao.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        scooterDao.delete(getScooterByIdHandler(id));
    }

    @Override
    public List<ScooterDto> getAllByRentalPointId(Long id) {
        return scooterDao.getScootersByScooterRentalPointId(id).stream()
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ScooterDto> getAvailableScootersByRentalPointId(Long id) {
        return scooterDao.getSAvailableScootersByScooterRentalPointId(id).stream()
                .map(scooterMapper::toDto)
                .collect(Collectors.toList());
    }

    private Scooter getScooterByIdHandler(Long id) {
        Optional<Scooter> optionalScooter = scooterDao.findById(id);
        if (optionalScooter.isPresent()) {
            return optionalScooter.get();
        } else {
            log.error("Error when trying to get scooter by id");
            throw new OperationCancelledException("Scooter was not found!");
        }
    }

    private boolean isDtoValid(ScooterDto dto) {
        if (dto.getModelId() != null && dto.getScooterRentalPointId() != null && dto.getStatus() != null
                && dto.getPassedHours() != null && dto.getPricePerHour() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update scooter");
            throw new OperationCancelledException("Invalid scooter parameters!");
        }
    }
}
