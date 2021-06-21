package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.enums.ScooterStatus;
import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scooters")
public class Scooter extends AEntity {

    @Builder
    public Scooter(Long id, Float pricePerHour, Integer passedHours) {
        super(id);
        this.pricePerHour = pricePerHour;
        this.passedHours = passedHours;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private ScooterModel model;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ScooterStatus status;

    @Column(name = "price_per_hour")
    private Float pricePerHour;

    @Column(name = "passed_hours")
    private Integer passedHours;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_point_id")
    private ScooterRentalPoint scooterRentalPoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scooter)) return false;
        Scooter scooter = (Scooter) o;
        return getModel().equals(scooter.getModel()) &&
                getStatus().equals(scooter.getStatus()) &&
                getPricePerHour().equals(scooter.getPricePerHour()) &&
                getPassedHours().equals(scooter.getPassedHours()) &&
                getScooterRentalPoint().equals(scooter.getScooterRentalPoint()) &&
                getId().equals(scooter.getId());
    }

    public Long getModelId() {
        return model != null ? model.getId() : null;
    }

    public Long getScooterRentalPointId() {
        return scooterRentalPoint != null ? scooterRentalPoint.getId() : null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getStatus(), getPricePerHour(), getPassedHours(),
                getScooterRentalPoint(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " Scooter; Status:" + getStatus() +
                "; Price per hour: " + getPricePerHour() + "; Passed hours: " + getPassedHours();
    }
}