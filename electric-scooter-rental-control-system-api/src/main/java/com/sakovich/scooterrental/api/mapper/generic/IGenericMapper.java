package com.sakovich.scooterrental.api.mapper.generic;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import com.sakovich.scooterrental.model.generic.AEntity;

public interface IGenericMapper<E extends AEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
