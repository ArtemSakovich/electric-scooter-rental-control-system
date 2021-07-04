package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.IScooterModelMapper;
import com.sakovich.scooterrental.repository.IScooterModelRepository;
import com.sakovich.scooterrental.model.ScooterModel;
import com.sakovich.scooterrental.model.dto.ScooterModelDto;
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
class ScooterModelServiceTest {

    @Mock
    IScooterModelRepository scooterModelDao;

    @InjectMocks
    ScooterModelService scooterModelService;

    @Mock
    IScooterModelMapper scooterModelMapper;

    ScooterModel scooterModel;

    @BeforeEach
    void setUp() {
        scooterModel = ScooterModel.builder().id(1L).title("Some title...").build();
    }

    @Test
    void getAll() {
        List<ScooterModel> scooterModels = new ArrayList<>();
        scooterModels.add(ScooterModel.builder().id(1L).title("Title_1").build());
        scooterModels.add(ScooterModel.builder().id(2L).title("Title_2").build());
        scooterModels.add(ScooterModel.builder().id(3L).title("Title_3").build());

        given(scooterModelDao.findAll()).willReturn(scooterModels);

        int receivedSize = scooterModelService.getAll().size();

        assertEquals(scooterModels.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(scooterModelDao.findById(1L)).willReturn(java.util.Optional.of(new ScooterModel()));
        given(scooterModelMapper.toDto(any(ScooterModel.class))).willReturn(new ScooterModelDto());
        ScooterModelDto returnedScooterModel = (scooterModelService.getById(1L));
        assertNotNull(returnedScooterModel);
    }

    @Test
    void save() {
        given(scooterModelDao.save(any(ScooterModel.class))).willReturn(new ScooterModel());
        given(scooterModelMapper.toDto(any(ScooterModel.class))).willReturn(new ScooterModelDto());
        given(scooterModelMapper.toEntity(any(ScooterModelDto.class))).willReturn(new ScooterModel());
        ScooterModelDto dto = new ScooterModelDto();
        dto.setTitle("Scooter model");
        ScooterModelDto savedScooterModel = scooterModelService.addScooterModel(dto);
        assertNotNull(savedScooterModel);
    }

    @Test
    void delete() {
        given(scooterModelDao.findById(1L)).willReturn(java.util.Optional.of(new ScooterModel()));
        scooterModelService.delete(1L);
        then(scooterModelDao).should(times(1)).delete(any(ScooterModel.class));
        verifyNoMoreInteractions(scooterModelDao);
    }
}