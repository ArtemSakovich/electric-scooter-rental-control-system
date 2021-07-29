package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.IScooterModelMapper;
import com.sakovich.scooterrental.api.service.IScooterModelService;
import com.sakovich.scooterrental.model.ScooterModel;
import com.sakovich.scooterrental.model.dto.ScooterModelDto;
import com.sakovich.scooterrental.repository.IScooterModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ScooterModelService implements IScooterModelService {

    private final IScooterModelRepository scooterModelRepository;
    private final IScooterModelMapper scooterModelMapper;

    @Override
    public ScooterModelDto addScooterModel(ScooterModelDto dto) {
        if (isDtoValid(dto)) {
            ScooterModel entity = scooterModelMapper.toEntity(dto);
            scooterModelRepository.save(entity);
        }
        return scooterModelMapper.toDto(scooterModelMapper.toEntity(dto));
    }

    @Override
    public List<ScooterModelDto> getAll() {
        return scooterModelRepository.findAll().stream()
                .map(scooterModelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScooterModelDto getById(Long id) {
        return scooterModelMapper.toDto(getScooterModelByIdHandler(id));
    }

    @Override
    public ScooterModelDto update(ScooterModelDto dto) {
        if (isDtoValid(dto)) {
            ScooterModel scooterModelToUpdate = getScooterModelByIdHandler(dto.getId());
            scooterModelToUpdate.setTitle(dto.getTitle());
            scooterModelRepository.save(scooterModelToUpdate);
        }
        return scooterModelMapper.toDto(scooterModelRepository.getById(dto.getId()));
    }

    @Override
    public void delete(Long id) {
        scooterModelRepository.delete(getScooterModelByIdHandler(id));
    }

    private ScooterModel getScooterModelByIdHandler(Long id) {
        Optional<ScooterModel> optionalScooterModel = scooterModelRepository.findById(id);
        if (optionalScooterModel.isPresent()) {
            return optionalScooterModel.get();
        } else {
            log.error("Error when trying to get scooter model by id");
            throw new OperationCancelledException("Scooter model was not found!");
        }
    }

    private boolean isDtoValid(ScooterModelDto dto) {
        if (dto.getTitle() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update scooter model");
            throw new OperationCancelledException("Invalid scooter model parameters!");
        }
    }
}
