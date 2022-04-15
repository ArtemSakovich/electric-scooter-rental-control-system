package com.sakovich.scooterrental.service;

import com.sakovich.scooterrental.api.exception.OperationCancelledException;
import com.sakovich.scooterrental.api.mapper.ICityMapper;
import com.sakovich.scooterrental.api.service.ICityService;
import com.sakovich.scooterrental.model.City;
import com.sakovich.scooterrental.model.dto.CityDto;
import com.sakovich.scooterrental.repository.ICityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Provides methods for transferring data from {@link ICityRepository} to CityController as well as processing.
 *
 * @author ArtemSakovich
 */
@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class CityService implements ICityService {

    /**
     * An implementation instance of a {@link ICityRepository} that provides access to its methods
     */
    private final ICityRepository cityDao;
    /**
     * An implementation instance of a {@link ICityMapper} that provides access to its methods
     */
    private final ICityMapper cityMapper;

    /**
     * Provides functionality to add a new city to the database. The essence of the method is to check the validity
     * of the {@link CityDto} that came from the UI and, in case of a successful check, map it to {@link City} tracked
     * by Hibernate. Then we save it to the database using the method of {@link ICityRepository}
     *
     * @param dto an instance of {@link CityDto} come from UI
     * @return an instance of {@link CityDto} to show the added entity on UI
     */
    @Override
    public CityDto addCity(CityDto dto) {
        if (isDtoValid(dto)) {
            cityDao.save(cityMapper.toEntity(dto));
        }
        return cityMapper.toDto(cityMapper.toEntity(dto));
    }

    /**
     * Provides functionality to get collection of {@link CityDto} as list to show it on the Ui. The essence of the
     * method is to get collection of {@link City} as list from {@link ICityRepository} turning it into a stream. Then map
     * each to {@link City} and reassemble it into collection of {@link CityDto} of entities for transferring to controller
     *
     * @return collection of {@link CityDto} as list to show it on the UI
     */
    @Override
    public List<CityDto> getAll() {
        return cityDao.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Provides functionality to get {@link CityDto} to show it on the Ui. The essence of the method is to get instance
     * of {@link City} from {@link ICityRepository} using private method to handle possible exception. Then map it to
     * {@link CityDto} and transfer to controller for representing on the UI
     *
     * @param id an identification number of city we want to receive
     * @return instance of {@link CityDto} to show it on the UI
     */
    @Override
    public CityDto getById(Long id) {
        return cityMapper.toDto(getCityByIdHandler(id));
    }

    /**
     * Provides functionality to update city in the database. The essence of the method is to check the validity
     * of the {@link CityDto} that came from the UI and, in case of a successful get instance of {@link City} using
     * private method to handle possible exception. Then set new name to updating city and save it to the database
     * using the method of {@link ICityRepository}
     *
     * @param dto an instance of {@link CityDto} come from UI
     * @return - an instance of {@link CityDto} to show the updated entity on UI
     */
    @Override
    public CityDto update(CityDto dto) {
        if (isDtoValid(dto)) {
            City cityToUpdate = getCityByIdHandler(dto.getId());
            cityToUpdate.setName(dto.getName());
            cityDao.save(cityToUpdate);
        }
        return cityMapper.toDto(cityDao.getById(dto.getId()));
    }

    /**
     * Provides functionality to delete city from the database. The essence of the method is get to get {@link City}
     * by id using private method to handle possible exception and then pass it as parameter to {@link ICityRepository} delete method
     *
     * @param id an identification number of city we want to delete
     */
    @Override
    public void delete(Long id) {
        cityDao.delete(getCityByIdHandler(id));
    }

    /**
     * Provides functionality to handle possible {@link EntityNotFoundException}. The essence of the method is to check
     * if come id is valid and return instance of {@link City} by this id if it's true. Otherwise log error and throw
     * custom {@link OperationCancelledException} with the corresponding message
     *
     * @param id an identification number of city we want to get
     * @return if city exists return instance of {@link City}, otherwise log error and throw custom exception
     */
    private City getCityByIdHandler(Long id) {
        Optional<City> optionalCity = cityDao.findById(id);
        if (optionalCity.isPresent()) {
            return optionalCity.get();
        } else {
            log.error("Error when trying to get city by id");
            throw new OperationCancelledException("City was not found!");
        }
    }

    /**
     * Provides functionality to check if dto come from UI is valid by checking it's field for null. If dto is not valid
     * throw custom {@link OperationCancelledException} with the corresponding message
     *
     * @param dto an instance of CityDto come from UI
     * @return if dto is valid return true, otherwise log error and throw custom exception
     */
    private boolean isDtoValid(CityDto dto) {
        if (dto.getName() != null) {
            return true;
        } else {
            log.error("Error when trying to add or update city");
            throw new OperationCancelledException("Invalid city parameters!");
        }
    }
}
