package com.sakovich.scooterrental.repository;

import com.sakovich.scooterrental.model.ScooterRentalPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScooterRentalPointRepository extends JpaRepository<ScooterRentalPoint, Long> {

    List<ScooterRentalPoint> findSByCity_Id(Long cityId);

    @Query("SELECT s.scooterRentalPoint FROM Scooter s where s.id = :scooterId")
    ScooterRentalPoint getByScooterId(@Param("scooterId") Long scooterId);
}
