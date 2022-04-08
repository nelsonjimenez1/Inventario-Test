package com.inventory.controllers;

import java.util.ArrayList;
import java.util.List;

import com.inventory.apis.UserControllerInterface;
import com.inventory.dtos.UserDTO;
import com.inventory.entities.UserDB;
import com.inventory.services.UserServiceInterface;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserControllerInterface {

	@Autowired
	private UserServiceInterface userService;
	@Autowired
	private ModelMapper modelMapper;

	public ResponseEntity<List<UserDTO>> getListUser() {
		return new ResponseEntity<List<UserDTO>>(convertListToDto(userService.getListUser()), HttpStatus.OK);
	}

	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		return new ResponseEntity<UserDTO>(convertToDto(userService.getUserById(id)), HttpStatus.OK);
	}

	public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(convertToDto(userService.editUser(convertToEntity(userDTO))), HttpStatus.OK);
	}

	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(convertToDto(userService.addUser(convertToEntity(userDTO))), HttpStatus.CREATED);
	}

	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("The user with id: " + String.valueOf(id.intValue()) + " was removed", HttpStatus.OK);
	}

	public ResponseEntity<List<UserDTO>> findByName(@PathVariable String name) {
		return new ResponseEntity<List<UserDTO>>(convertListToDto(userService.findByName(name)), HttpStatus.OK);
	}

	private List<UserDTO> convertListToDto(List<UserDB> listUserDB) {
		List<UserDTO> listUserDTO = new ArrayList<>();
		for (UserDB userDB: listUserDB) {
			listUserDTO.add(convertToDto(userDB));
		}

		return listUserDTO;
	}

	private UserDB convertToEntity(UserDTO userDTO) {
    return modelMapper.map(userDTO, UserDB.class);
	}

	private UserDTO convertToDto(UserDB userDB) {
    return modelMapper.map(userDB, UserDTO.class);
	}
}
