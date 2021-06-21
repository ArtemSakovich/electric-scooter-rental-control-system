package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.ScooterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScooterModelDao extends JpaRepository<ScooterModel, Long> {
}
