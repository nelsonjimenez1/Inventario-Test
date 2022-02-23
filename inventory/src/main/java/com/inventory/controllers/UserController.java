package com.inventory.controllers;

import java.util.List;

import com.inventory.apis.UserControllerInterface;
import com.inventory.entities.UserDB;
import com.inventory.services.UserServiceInterface;

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

	public ResponseEntity<List<UserDB>> getListUser() {
		return new ResponseEntity<List<UserDB>>(userService.getListUser(), HttpStatus.OK);
	}

	public ResponseEntity<UserDB> getUserById(@PathVariable Long id) {
		return new ResponseEntity<UserDB>(userService.getUserById(id), HttpStatus.OK);
	}

	public ResponseEntity<UserDB> editUser(@RequestBody UserDB user) {
		return new ResponseEntity<UserDB>(userService.editUser(user), HttpStatus.OK);
	}

	public ResponseEntity<UserDB> addUser(@RequestBody UserDB user) {
		return new ResponseEntity<UserDB>(userService.addUser(user), HttpStatus.CREATED);
	}

	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("The user with id: " + String.valueOf(id.intValue()) + " was removed", HttpStatus.OK);
	}

	public ResponseEntity<List<UserDB>> findByName(@PathVariable String name) {
		return new ResponseEntity<List<UserDB>>(userService.findByName(name), HttpStatus.OK);
	}
}
