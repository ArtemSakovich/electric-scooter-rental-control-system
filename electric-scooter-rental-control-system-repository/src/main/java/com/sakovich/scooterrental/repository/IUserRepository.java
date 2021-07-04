package com.sakovich.scooterrental.repository;

import com.sakovich.scooterrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

    Boolean existsByEmail(String email);
}
