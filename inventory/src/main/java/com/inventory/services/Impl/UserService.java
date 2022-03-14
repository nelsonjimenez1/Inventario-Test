package com.inventory.services.Impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

import com.inventory.entities.UserDb;
import com.inventory.exceptions.UserException;
import com.inventory.repositories.UserRepository;
import com.inventory.services.UserServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {
  @Autowired
  private UserRepository userRepository;

  public UserDb addUser(UserDb user) {
    if (validateUser(user)) {
      return userRepository.save(user);
    } else {
      throw new UserException("User not valid");
    }
  }

  public boolean validateUser(UserDb user) {
    if (user.getName().equals("") || user.getLastName().equals("") || user.getTypeDocument().equals("") || user.getDocumentNumber().equals("") || user.getPosition().equals("") || user.getSalary() <= 0) {
      return false;
    } else if (user.getBirthDate().toString().equals("") || user.getBondingDate().toString().equals("")) {
      return false;
    } else if (!validateDateFormat(user.getBirthDate()) || !validateDateFormat(user.getBondingDate())) {
      return false;      
    } else if (calculateAge(user.getBirthDate().toLocalDate()) < 18) {
      return false;
    } 
    return true;
  }

  public boolean validateDateFormat(Date date) {
    Boolean valid = true;
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      dateFormat.setLenient(false);
      dateFormat.parse(date.toLocalDate().getYear() + "-" + date.toLocalDate().getMonthValue() + "-" + date.toLocalDate().getDayOfMonth());
      valid = true;
    } catch (ParseException e) {
      valid = false;
    }

    return valid;
  }

  public int calculateAge(LocalDate dob) {  
    LocalDate curDate = LocalDate.now();  
    if ((dob != null) && (curDate != null)) {    
      return Period.between(dob, curDate).getYears();  
    } else {  
      return 0;  
    }
  }  
}
