package com.inventory.controllers;

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
public class UserController implements UserControllerInterface{

    @Autowired
    private UserServiceInterface userService;

    public ResponseEntity login(@RequestBody UserDB user) {
        return new ResponseEntity(user, HttpStatus.OK);
    }

    public ResponseEntity signUp(@RequestBody UserDB user) {
        return new ResponseEntity(userService.signUp(user), HttpStatus.CREATED);
    }

    public ResponseEntity getUserByUser(@PathVariable String user) {
        return new ResponseEntity(userService.getUserByUser(user), HttpStatus.OK);
    }
}
