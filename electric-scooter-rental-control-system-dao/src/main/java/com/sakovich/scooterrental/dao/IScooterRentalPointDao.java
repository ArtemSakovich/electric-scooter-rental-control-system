package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.ScooterRentalPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScooterRentalPointDao extends JpaRepository<ScooterRentalPoint, Long> {

    List<ScooterRentalPoint> findScooterRentalPointByCity_Id(Long cityId);

    @Query("SELECT s.scooterRentalPoint FROM Scooter s where s.id = :scooterId")
    ScooterRentalPoint getScooterRentalPointByScooterId(@Param("scooterId") Long scooterId);
}
