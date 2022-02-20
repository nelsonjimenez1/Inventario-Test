package com.inventory.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class UserDB {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, name = "usua")
    private String user;
    private String password;
    private String name;
    private int age;
    private Role role;
    private Date admissionDate;

    public UserDB() {
  
    }

    public UserDB(long id, String user, String password, String name, int age, Role role, Date admissionDate) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = name;
        this.age = age;
        this.role = role;
        this.admissionDate = admissionDate;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(Date admissionDate) {
        this.admissionDate = admissionDate;
    }
}
