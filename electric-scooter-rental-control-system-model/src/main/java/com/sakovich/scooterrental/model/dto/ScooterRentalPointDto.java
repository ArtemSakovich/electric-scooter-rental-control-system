package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScooterRentalPointDto extends AbstractDto {

    private Long cityId;
    private String street;
    private String buildingNumber;
}
