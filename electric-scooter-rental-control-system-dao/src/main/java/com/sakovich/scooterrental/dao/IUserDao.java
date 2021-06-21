package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User, Long> {

    User getUserByEmail(String email);

    Boolean existsByEmail(String email);
}
