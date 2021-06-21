package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class RideSessionDto extends AbstractDto {

    private Long userId;
    private Long scooterId;
    private Long returnScooterRentalPointId;
    private Integer rideHours;
    private Double price;
    private Timestamp createdOn;
}
