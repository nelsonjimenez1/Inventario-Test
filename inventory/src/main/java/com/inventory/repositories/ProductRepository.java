package com.inventory.repositories;

import java.sql.Date;
import java.util.List;

import com.inventory.entities.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {  

  @Query("SELECT p from Product p WHERE p.name = :name")
  Product findByName(@Param("name") String name);
}
