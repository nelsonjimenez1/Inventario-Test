package com.inventory.services.Impl;

import com.inventory.entities.UserDB;
import com.inventory.exceptions.UserException;
import com.inventory.repositories.UserRepository;
import com.inventory.services.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
  @Autowired
  private UserRepository userRepository;

  public UserDB login(UserDB user) {
    UserDB userFind = userRepository.findByUser(user.getUser());
    if (user.getPassword().equals(userFind.getPassword())) {
      return userFind;
    } else {
      throw new UserException("Login failed");
    }
  }

  public UserDB signUp(UserDB user) {
    UserDB findByUserDB = userRepository.findByUser(user.getUser());
    if (findByUserDB == null) {
      return userRepository.save(user);
    } else {
      throw new UserException("Repeated user");
    }
  }

  public UserDB getUserByUser(String user) {
    UserDB findByUserDB = userRepository.findByUser(user);
    if (findByUserDB != null) {
      return findByUserDB;
    } else {
      throw new UserException("User not found");
    }
  }

  public UserDB getUserByUserWithouthException(String user) {
    UserDB findByUserDB = userRepository.findByUser(user);
    return findByUserDB;
  }
}
