package com.sakovich.scooterrental.repository;

import com.sakovich.scooterrental.model.RideSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRideSessionRepository extends JpaRepository<RideSession, Long> {

    List<RideSession> getByUserId(Long id);

    List<RideSession> getByScooterId(Long id);
}
