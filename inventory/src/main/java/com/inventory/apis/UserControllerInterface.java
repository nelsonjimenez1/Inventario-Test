package com.inventory.apis;

import com.inventory.entities.UserDB;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserControllerInterface {

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserDB user);

  @PostMapping()
  public ResponseEntity signUp(@RequestBody UserDB user);

  @GetMapping("/{user}")
  public ResponseEntity getUserByUser(@PathVariable String user);
}