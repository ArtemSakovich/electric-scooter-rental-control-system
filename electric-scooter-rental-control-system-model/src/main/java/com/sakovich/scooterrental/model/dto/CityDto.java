package com.sakovich.scooterrental.model.dto;

import com.sakovich.scooterrental.model.dto.generic.AbstractDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto extends AbstractDto {

    @Builder
    public CityDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    private String name;
}
