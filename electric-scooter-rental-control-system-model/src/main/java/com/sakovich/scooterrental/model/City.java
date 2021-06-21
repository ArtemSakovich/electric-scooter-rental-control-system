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
@Table(name = "cities")
public class City extends AEntity {

    @Builder
    public City(Long id, String name) {
        super(id);
        this.name = name;
    }

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<ScooterRentalPoint> scooterRentalPoints;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return getName().equals(city.getName()) &&
                getScooterRentalPoints().equals(city.getScooterRentalPoints()) &&
                getId().equals(city.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getScooterRentalPoints(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " City: " + getName();
    }
}