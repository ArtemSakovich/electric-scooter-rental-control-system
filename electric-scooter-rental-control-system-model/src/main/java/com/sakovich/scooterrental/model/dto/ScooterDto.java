package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import com.sakovich.scooterrental.model.enums.ScooterStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScooterDto extends AbstractDto {

    private Long modelId;
    private ScooterStatus status;
    private Float pricePerHour;
    private Integer passedHours;
    private Long scooterRentalPointId;
}