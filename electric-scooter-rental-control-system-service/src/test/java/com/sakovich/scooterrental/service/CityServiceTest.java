package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.ICityMapper;
import com.sakovich.scooterrental.model.City;
import com.sakovich.scooterrental.model.dto.CityDto;
import com.sakovich.scooterrental.repository.ICityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    ICityRepository cityDao;

    @InjectMocks
    CityService cityService;

    @Mock
    ICityMapper cityMapper;

    private final CityDto cityDto = CityDto.builder().id(1L).name("Hrodna").build();

    @Test
    void getAll() {
        List<City> cities = new ArrayList<>();
        cities.add(cityMapper.toEntity(cityDto));

        given(cityDao.findAll()).willReturn(cities);

        int receivedSize = cityService.getAll().size();

        assertEquals(cities.size(), receivedSize);
    }

    @Test
    void findById() {
        given(cityDao.findById(1L)).willReturn(java.util.Optional.of(new City()));
        given(cityMapper.toDto(any(City.class))).willReturn(new CityDto());
        CityDto returnedCity = cityService.getById(1L);
        assertNotNull(returnedCity);
    }

    @Test
    void save() {
        given(cityDao.save(any(City.class))).willReturn(new City());
        given(cityMapper.toDto(any(City.class))).willReturn(new CityDto());
        given(cityMapper.toEntity(any(CityDto.class))).willReturn(new City());
        CityDto savedCity = cityService.addCity(cityDto);
        assertNotNull(savedCity);
    }

    @Test
    void delete() {
        given(cityDao.findById(1L)).willReturn(java.util.Optional.of(new City()));
        cityService.delete(1L);
        then(cityDao).should(times(1)).delete(any(City.class));
        verifyNoMoreInteractions(cityDao);
    }
}