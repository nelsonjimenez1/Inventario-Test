package com.inventory.dtos;

import com.inventory.entities.Role;

public class UserDTO {

	private long id;
	private String name;
	private Role role;
	private Boolean active;

	public UserDTO() {
    
	}

	public UserDTO(long id, String name, Role role, Boolean active) {
		try {
			validateName(name);
			validateRole(role);
			validateActive(active);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.id = id;
		this.name = name;
		this.role = role;
		this.active = active;
	}

	private void validateName(String name) throws Exception {
		if (name == null) {
			throw new Exception("name is null");
		} else if (name.equals("")) {
			throw new Exception("name is empty");
		}
	}

	private void validateRole(Role role) throws Exception {
		if (role == null) {
			throw new Exception("role is null");
		}
	}

	private void validateActive(Boolean active) throws Exception {
		if (active == null) {
			throw new Exception("active is null");
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
