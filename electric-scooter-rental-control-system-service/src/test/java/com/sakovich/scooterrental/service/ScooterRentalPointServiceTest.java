package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.IScooterRentalPointMapper;
import com.sakovich.scooterrental.repository.IScooterRentalPointRepository;
import com.sakovich.scooterrental.model.ScooterRentalPoint;
import com.sakovich.scooterrental.model.dto.ScooterRentalPointDto;
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
class ScooterRentalPointServiceTest {

    @Mock
    IScooterRentalPointRepository scooterRentalPointDao;

    @InjectMocks
    ScooterRentalPointService scooterRentalPointService;

    @Mock
    IScooterRentalPointMapper scooterRentalPointMapper;

    ScooterRentalPoint scooterRentalPoint;

    @BeforeEach
    void setUp() {
        scooterRentalPoint = ScooterRentalPoint.builder().id(1L).street("Some street...")
                .buildingNumber("Some building number...").build();
    }

    @Test
    void getAll() {
        List<ScooterRentalPoint> scooterRentalPoints = new ArrayList<>();
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(1L).street("Street_1").buildingNumber("12").build());
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(2L).street("Street_2").buildingNumber("13").build());
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(3L).street("Street_3").buildingNumber("14").build());

        given(scooterRentalPointDao.findAll()).willReturn(scooterRentalPoints);

        int receivedSize = scooterRentalPointService.getAll().size();

        assertEquals(scooterRentalPoints.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(scooterRentalPointDao.findById(1L)).willReturn(java.util.Optional.of(new ScooterRentalPoint()));
        given(scooterRentalPointMapper.toDto(any(ScooterRentalPoint.class))).willReturn(new ScooterRentalPointDto());
        ScooterRentalPointDto returnedScooterRentalPoint = (scooterRentalPointService.getById(1L));
        assertNotNull(returnedScooterRentalPoint);
    }

    @Test
    void save() {
        given(scooterRentalPointDao.save(any(ScooterRentalPoint.class))).willReturn(new ScooterRentalPoint());
        given(scooterRentalPointMapper.toDto(any(ScooterRentalPoint.class))).willReturn(new ScooterRentalPointDto());
        given(scooterRentalPointMapper.toEntity(any(ScooterRentalPointDto.class))).willReturn(new ScooterRentalPoint());
        ScooterRentalPointDto dto = new ScooterRentalPointDto();
        dto.setCityId(1L);
        dto.setStreet("Street");
        dto.setBuildingNumber("Number");
        ScooterRentalPointDto savedScooterRentalPoint = scooterRentalPointService.addRentalPoint(dto);
        assertNotNull(savedScooterRentalPoint);
    }

    @Test
    void delete() {
        given(scooterRentalPointDao.findById(1L)).willReturn(java.util.Optional.of(new ScooterRentalPoint()));
        scooterRentalPointService.delete(1L);
        then(scooterRentalPointDao).should(times(1)).delete(any(ScooterRentalPoint.class));
        verifyNoMoreInteractions(scooterRentalPointDao);
    }

    @Test
    void getAllScooterRentalPointsByCityId() {
        List<ScooterRentalPoint> scooterRentalPoints = new ArrayList<>();
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(1L).street("Street_1").buildingNumber("12").build());
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(2L).street("Street_2").buildingNumber("13").build());
        scooterRentalPoints.add(ScooterRentalPoint.builder().id(3L).street("Street_3").buildingNumber("14").build());

        given(scooterRentalPointDao.findSByCity_Id(1L)).willReturn(scooterRentalPoints);
        given(scooterRentalPointMapper.toDto(any(ScooterRentalPoint.class))).willReturn(new ScooterRentalPointDto());
        int receivedSize = scooterRentalPointService.getAllScooterRentalPointsByCity(1L).size();

        assertEquals(scooterRentalPoints.size(), receivedSize);
    }

    @Test
    void getScooterRentalPointByScooterId() {
        given(scooterRentalPointDao.getByScooterId(1L)).willReturn(new ScooterRentalPoint());
        given(scooterRentalPointMapper.toDto(any(ScooterRentalPoint.class))).willReturn(new ScooterRentalPointDto());
        ScooterRentalPointDto returnedScooterRentalPoint = (scooterRentalPointService.getScooterRentalPointByScooterId(1L));
        assertNotNull(returnedScooterRentalPoint);
    }
}