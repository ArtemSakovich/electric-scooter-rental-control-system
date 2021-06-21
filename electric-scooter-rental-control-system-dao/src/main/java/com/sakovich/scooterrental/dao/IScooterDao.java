package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScooterDao extends JpaRepository<Scooter, Long> {

    @Query("FROM Scooter s where s.scooterRentalPoint.id = :id")
    List<Scooter> getScootersByScooterRentalPointId(Long id);

    @Query("FROM Scooter s where s.scooterRentalPoint.id = :id and s.status = 'AVAILABLE'")
    List<Scooter> getSAvailableScootersByScooterRentalPointId(Long id);
}
