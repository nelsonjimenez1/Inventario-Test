package com.inventory.services.Impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

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

  public List<UserDB> getListUser() {
    List<UserDB> findAll = userRepository.findAll();
    if (findAll.size() == 0 || findAll == null) {
      throw new UserException("Empty list user");
    } else {
      return findAll;
    }
  }

  public UserDB getUserById(Long id) {
    Optional<UserDB> userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      return userOpt.get();
    } else {
      throw new UserException("User not found");
    }
  }

  public UserDB editUser(UserDB user) {
    Optional<UserDB> userOpt = userRepository.findById(user.getId());
    if (userOpt.isPresent()) {
      try {
        return userRepository.save(user);
      } catch (Exception e) {
        throw new UserException("User repeated name");
      }
    } else {
      throw new UserException("User not found");
    }
  }

  public UserDB addUser(UserDB user) {
    UserDB userFind = userRepository.findByName(user.getName());
    if (userFind != null) {
      throw new UserException("User repeated name");
    } else {
      return userRepository.save(user);
    }
  }

  public Long deleteUser(Long id) {
    Optional<UserDB> userFind = userRepository.findById(id);
    if (userFind.isPresent()) {
      userRepository.deleteById(id);
      return id;
    } else {
      throw new UserException("User not found");
    }
  }

  public List<UserDB> findByName(String name) {
    List<UserDB> findByName = userRepository.findAllByName(MessageFormat.format("{0}" + name + "{0}", "%"));
    if (findByName.size() == 0 || findByName == null) {
      throw new UserException("Empty list user");
    } else {
      return findByName;
    }
  }
}
