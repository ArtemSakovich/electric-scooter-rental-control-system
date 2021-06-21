package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.IScooterMapper;
import com.sakovich.scooterrental.dao.IScooterDao;
import com.sakovich.scooterrental.model.Scooter;
import com.sakovich.scooterrental.model.dto.ScooterDto;
import com.sakovich.scooterrental.model.enums.ScooterStatus;
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
class ScooterServiceTest {

    @Mock
    IScooterDao scooterDao;

    @InjectMocks
    ScooterService scooterService;

    @Mock
    IScooterMapper scooterMapper;

    Scooter scooter;

    @BeforeEach
    void setUp() {
        scooter = Scooter.builder().id(1L).pricePerHour(12.5f).passedHours(50).build();
    }

    @Test
    void getAll() {
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(Scooter.builder().id(1L).pricePerHour(12.5f).passedHours(50).build());
        scooters.add(Scooter.builder().id(2L).pricePerHour(13.5f).passedHours(55).build());
        scooters.add(Scooter.builder().id(3L).pricePerHour(15.5f).passedHours(57).build());

        given(scooterDao.findAll()).willReturn(scooters);

        int receivedSize = scooterService.getAll().size();

        assertEquals(scooters.size(), receivedSize);
    }

    @Test
    void findByIdFound() {
        given(scooterDao.findById(1L)).willReturn(java.util.Optional.of(new Scooter()));
        given(scooterMapper.toDto(any(Scooter.class))).willReturn(new ScooterDto());
        ScooterDto returnedScooter = (scooterService.getById(1L));
        assertNotNull(returnedScooter);
    }

    @Test
    void save() {
        given(scooterDao.save(any(Scooter.class))).willReturn(new Scooter());
        given(scooterMapper.toDto(any(Scooter.class))).willReturn(new ScooterDto());
        given(scooterMapper.toEntity(any(ScooterDto.class))).willReturn(new Scooter());
        ScooterDto dto = new ScooterDto();
        dto.setModelId(1L);
        dto.setScooterRentalPointId(1L);
        dto.setPassedHours(12);
        dto.setStatus(ScooterStatus.BUSY);
        dto.setPricePerHour(12.50f);
        ScooterDto savedScooter = scooterService.addScooter(dto);
        assertNotNull(savedScooter);
    }

    @Test
    void delete() {
        given(scooterDao.findById(1L)).willReturn(java.util.Optional.of(new Scooter()));
        scooterService.delete(1L);
        then(scooterDao).should(times(1)).delete(any(Scooter.class));
        verifyNoMoreInteractions(scooterDao);
    }

    @Test
    void getAvailableScooters() {
        List<Scooter> scooters = new ArrayList<>();
        scooters.add(Scooter.builder().id(1L).pricePerHour(12.5f).passedHours(50).build());
        scooters.add(Scooter.builder().id(2L).pricePerHour(13.5f).passedHours(55).build());
        scooters.add(Scooter.builder().id(3L).pricePerHour(15.5f).passedHours(57).build());

        given(scooterDao.getSAvailableScootersByScooterRentalPointId(1L)).willReturn(scooters);

        int receivedSize = scooterService.getAvailableScootersByRentalPointId(1L).size();

        assertEquals(scooters.size(), receivedSize);
    }
}