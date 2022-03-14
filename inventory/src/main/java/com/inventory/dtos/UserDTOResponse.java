package com.inventory.dtos;

public class UserDtoResponse {
	private String bondingDate;
	private String age;

	public UserDtoResponse(String bondingDate, String age) {
		this.bondingDate = bondingDate;
		this.age = age;
	}

	public String getBondingDate() {
		return bondingDate;
	}
	public void setBondingDate(String bondingDate) {
		this.bondingDate = bondingDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
}
