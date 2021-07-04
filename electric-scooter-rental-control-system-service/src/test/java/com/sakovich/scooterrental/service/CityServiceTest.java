package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.mapper.ICityMapper;
import com.sakovich.scooterrental.repository.ICityRepository;
import com.sakovich.scooterrental.model.City;
import com.sakovich.scooterrental.model.dto.CityDto;
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
class CityServiceTest {

    @Mock
    ICityRepository cityDao;

    @InjectMocks
    CityService cityService;

    @Mock
    ICityMapper cityMapper;

    City city;

    @BeforeEach
    void setUp() {
        city = City.builder().id(1L).name("Some name...").build();
    }

    @Test
    void getAll() {
        List<City> cities = new ArrayList<>();
        cities.add(City.builder().id(1L).name("Hrodna").build());
        cities.add(City.builder().id(2L).name("Minsk").build());
        cities.add(City.builder().id(3L).name("Vitebsk").build());

        given(cityDao.findAll()).willReturn(cities);

        int receivedSize = cityService.getAll().size();

        assertEquals(cities.size(), receivedSize);
    }

    @Test
    void findById() {
        given(cityDao.findById(1L)).willReturn(java.util.Optional.of(new City()));
        given(cityMapper.toDto(any(City.class))).willReturn(new CityDto());
        CityDto returnedCity = (cityService.getById(1L));
        assertNotNull(returnedCity);
    }

    @Test
    void save() {
        given(cityDao.save(any(City.class))).willReturn(new City());
        given(cityMapper.toDto(any(City.class))).willReturn(new CityDto());
        given(cityMapper.toEntity(any(CityDto.class))).willReturn(new City());
        CityDto city = new CityDto();
        city.setName("Hrodna");
        CityDto savedCity = cityService.addCity(city);
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