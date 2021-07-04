package com.sakovich.scooterrental.repository;

import com.sakovich.scooterrental.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    Role getByName(String name);
}
