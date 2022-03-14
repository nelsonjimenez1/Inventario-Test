package com.inventory.repositories;

import com.inventory.entities.UserDb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDb, Long> {

}
