package com.sakovich.scooterrental.dao;

import com.sakovich.scooterrental.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);
}
