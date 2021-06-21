package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "scooter_models")
public class ScooterModel extends AEntity {

    @Builder
    public ScooterModel(Long id, String title) {
        super(id);
        this.title = title;
    }

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "model", cascade = CascadeType.ALL)
    private List<Scooter> scooters;

    public ScooterModel() {
        scooters = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScooterModel)) return false;
        ScooterModel scooterModel = (ScooterModel) o;
        return getTitle().equals(scooterModel.getTitle()) &&
                getScooters().equals(scooterModel.getScooters()) &&
                getId().equals(scooterModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getScooters(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " Title: " + getTitle() + "; Scooters: " + getScooters();
    }
}
