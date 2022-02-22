package com.inventory.apis;

import java.util.List;

import com.inventory.entities.UserDB;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserControllerInterface {
	@GetMapping()
	public ResponseEntity<List<UserDB>> getListUser();

	@GetMapping("/{id}")
	public ResponseEntity<UserDB> getUserById(@PathVariable Long id);

	@PutMapping()
	public ResponseEntity<UserDB> editUser(@RequestBody UserDB user);

	@PostMapping()
	public ResponseEntity<UserDB> addUser(@RequestBody UserDB user);

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id);

	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDB>> findByName(@PathVariable String name);
}
