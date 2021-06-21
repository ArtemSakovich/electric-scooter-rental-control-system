package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubscriptionDao extends JpaRepository<Subscription, Long> {

    @Query("FROM Subscription s where s.user.id = :userId")
    List<Subscription> findAllByUserId(@Param("userId") Long userId);
}
