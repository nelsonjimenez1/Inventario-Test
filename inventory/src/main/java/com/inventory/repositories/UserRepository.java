package com.inventory.repositories;

import java.util.List;

import com.inventory.entities.UserDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDB, Long> {

  @Query("SELECT u from UserDB u WHERE u.name = :name")
  UserDB findByName(@Param("name") String name);

  @Query("SELECT u from UserDB u WHERE u.name LIKE :name")
  List<UserDB> findAllByName(@Param("name") String name);
}
