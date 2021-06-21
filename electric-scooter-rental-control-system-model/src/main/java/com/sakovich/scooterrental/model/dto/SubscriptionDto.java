package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import com.sakovich.scooterrental.model.enums.SubscriptionStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SubscriptionDto extends AbstractDto {

    private Long userId;
    private Double price;
    private Double balance;
    private Float discountPercentage;
    private SubscriptionStatus status;
}
