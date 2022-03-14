package com.inventory.apis;

import com.inventory.dtos.UserDto;
import com.inventory.dtos.UserDtoResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserControllerInterface {
	@PostMapping()
	public ResponseEntity<UserDtoResponse> addUser(@RequestBody UserDto userDto);
}
