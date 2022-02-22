package com.inventory.services;

import java.util.List;

import com.inventory.entities.UserDB;

public interface UserServiceInterface {

	public List<UserDB> getListUser();

	public UserDB getUserById(Long id);

	public UserDB editUser(UserDB user);

	public UserDB addUser(UserDB user);

	public Long deleteUser(Long id);

	public List<UserDB> findByName(String name);
}
