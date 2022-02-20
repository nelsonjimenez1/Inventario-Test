package com.inventory.repositories;

import java.sql.Date;
import java.util.List;

import com.inventory.entities.UserDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Long> {
  UserDB findByUser(String user);
}
