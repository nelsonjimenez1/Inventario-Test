/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author nelso
 */
@Entity
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private int amount;
    private Date admissionDate;
    @OneToOne
    @JoinColumn(name = "user_id", updatable = true, nullable = false)
    private UserDB userDB;
    
    public Product() {

    }

    public Product(Long id, String name, int amount, Date admissionDate, UserDB userDB) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.admissionDate = admissionDate;
        this.userDB = userDB;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }

    public UserDB getUserDB() {
        return userDB;
    } 

    public void setUserDB(UserDB userDB) {
        this.userDB = userDB;
    }
}
