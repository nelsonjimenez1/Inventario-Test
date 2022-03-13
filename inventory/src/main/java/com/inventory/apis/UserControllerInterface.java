package com.inventory.apis;

import com.inventory.dtos.UserDTO;
import com.inventory.dtos.UserDTOResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserControllerInterface {
	@PostMapping()
	public ResponseEntity<UserDTOResponse> addUser(@RequestBody UserDTO userDto);
}
