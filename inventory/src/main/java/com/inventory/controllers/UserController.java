package com.inventory.controllers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

import com.inventory.apis.UserControllerInterface;
import com.inventory.dtos.UserDto;
import com.inventory.dtos.UserDtoResponse;
import com.inventory.entities.UserDb;
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

	public ResponseEntity<UserDtoResponse> addUser(@RequestBody UserDto userDto) {
		UserDb userDb = null;
		try {
			userDb = convertToEntity(userDto);
			userDto  = convertToDto(userService.addUser(userDb));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String bondingDate = calculateTime(userDto.getBondingDate().toLocalDate());
		String age = calculateTime(userDto.getBirthDate().toLocalDate());
		UserDtoResponse response = new UserDtoResponse(bondingDate, age);
		return new ResponseEntity<UserDtoResponse>(response, HttpStatus.CREATED);
	}

	private UserDb convertToEntity(UserDto userDto) throws ParseException {
    return modelMapper.map(userDto, UserDb.class);
	}

	private UserDto convertToDto(UserDb userDB) throws ParseException {
    return modelMapper.map(userDB, UserDto.class);
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
