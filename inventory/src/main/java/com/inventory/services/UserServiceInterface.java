package com.inventory.services;

import javax.jws.soap.SOAPBinding.Use;

import com.inventory.entities.UserDB;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

public interface UserServiceInterface {

  public UserDB login(UserDB user);

  public UserDB signUp(UserDB user);

  public UserDB getUserByUser(String user);

  public UserDB getUserByUserWithouthException(String user);

}
