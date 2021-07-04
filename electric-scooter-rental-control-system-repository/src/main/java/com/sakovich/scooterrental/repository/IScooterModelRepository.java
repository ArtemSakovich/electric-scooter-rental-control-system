package com.sakovich.scooterrental.repository;

import com.sakovich.scooterrental.model.ScooterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScooterModelRepository extends JpaRepository<ScooterModel, Long> {
}
