package com.sakovich.scooterrental.model;

import com.sakovich.scooterrental.model.generic.AEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ride_sessions")
public class RideSession extends AEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scooter_id")
    private Scooter scooter;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "ride_hours")
    private Integer rideHours;

    @Column(name = "price")
    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RideSession)) return false;
        RideSession rideSession = (RideSession) o;
        return getUser().equals(rideSession.getUser()) &&
                getScooter().equals(rideSession.getScooter()) &&
                getRideHours().equals(rideSession.getRideHours()) &&
                getPrice().equals(rideSession.getPrice()) &&
                getId().equals(rideSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getScooter(), getRideHours(), getPrice(), getId());
    }

    @Override
    public String toString() {
        return "#" + getId() + " Ride session; User: " + getUser() + "; Scooter: " + getScooter() +
                "; Ride hours:" + getRideHours() + "; Price for ride: " + getPrice();
    }
}
