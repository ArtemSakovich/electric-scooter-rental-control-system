package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScooterModelDto extends AbstractDto {

    String title;
}
