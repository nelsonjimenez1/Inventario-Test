package com.inventory.apis;

import java.util.List;

import com.inventory.dtos.UserDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
public interface UserControllerInterface {
	@GetMapping()
	public ResponseEntity<List<UserDTO>> getListUser();

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id);

	@PutMapping()
	public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO);

	@PostMapping()
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id);

	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByName(@PathVariable String name);
}
