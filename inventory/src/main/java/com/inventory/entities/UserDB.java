package com.inventory.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDB {

	@Id
	@GeneratedValue
	private long id;
	@Column(unique = true)
	private String name;
	private Role role;
	private Boolean active;

	public UserDB() {

	}

	public UserDB(long id, String name, Role role, Boolean active) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.active = active;
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
