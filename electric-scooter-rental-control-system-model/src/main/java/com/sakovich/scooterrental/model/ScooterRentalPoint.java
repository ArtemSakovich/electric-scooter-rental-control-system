package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "scooter_rental_points")
public class ScooterRentalPoint extends AEntity {

    @Builder
    public ScooterRentalPoint(Long id, String street, String buildingNumber) {
        super(id);
        this.street = street;
        this.buildingNumber = buildingNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "street")
    private String street;

    @Column(name = "building_number")
    private String buildingNumber;

    @OneToMany(mappedBy = "scooterRentalPoint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Scooter> scooters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScooterRentalPoint)) return false;
        ScooterRentalPoint scooterRentalPoint = (ScooterRentalPoint) o;
        return getCity().equals(scooterRentalPoint.getCity()) &&
                getStreet().equals(scooterRentalPoint.getStreet()) &&
                getBuildingNumber().equals(scooterRentalPoint.getBuildingNumber()) &&
                getScooters().equals(scooterRentalPoint.getScooters()) &&
                getId().equals(scooterRentalPoint.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getCity(), getBuildingNumber(), getScooters(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " Scooter rental point; Street: " + getStreet() +
                "; Building number: " + getBuildingNumber();
    }
}