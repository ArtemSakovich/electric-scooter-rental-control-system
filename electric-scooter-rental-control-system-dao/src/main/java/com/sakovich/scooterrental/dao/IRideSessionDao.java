package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.RideSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRideSessionDao extends JpaRepository<RideSession, Long> {

    List<RideSession> getRideSessionsByUserId(Long id);

    List<RideSession> getRideSessionsByScooterId(Long id);
}
