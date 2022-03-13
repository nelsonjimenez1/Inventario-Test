package com.inventory.controllers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

import com.inventory.apis.UserControllerInterface;
import com.inventory.dtos.UserDTO;
import com.inventory.dtos.UserDTOResponse;
import com.inventory.entities.UserDB;
import com.inventory.services.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserControllerInterface {

	@Autowired
	private UserServiceInterface userService;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<UserDTOResponse> addUser(@RequestBody UserDTO userDto) {
		UserDB userDB = null;
		UserDTO userDTOResponse = null;
		try {
			userDB = convertToEntity(userDto);
			userDTOResponse  = convertToDto(userService.addUser(userDB));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String bondingDate = calculateTime(userDTOResponse.getBondingDate().toLocalDate());
		String age = calculateTime(userDTOResponse.getBirthDate().toLocalDate());
		UserDTOResponse response = new UserDTOResponse(bondingDate, age);
		return new ResponseEntity<UserDTOResponse>(response, HttpStatus.CREATED);
	}

	private UserDB convertToEntity(UserDTO userDto) throws ParseException {
    return modelMapper.map(userDto, UserDB.class);
	}

	private UserDTO convertToDto(UserDB userDB) throws ParseException {
    return modelMapper.map(userDB, UserDTO.class);
	}

	public String calculateTime(LocalDate dob) {  
    LocalDate curDate = LocalDate.now();  
    if ((dob != null) && (curDate != null)) {    
			Period between = Period.between(dob, curDate);  
			return String.valueOf(between.getYears()) + " años " + String.valueOf(between.getMonths()) + " meses " + String.valueOf(between.getDays()) + " dias";  
    } else {  
      return "";  
    }
  } 
}
