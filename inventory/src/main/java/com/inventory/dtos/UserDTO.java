package com.inventory.dtos;

import java.sql.Date;

public class UserDto {
	private long id;
	private String name;
	private String lastName;
	private String typeDocument;
	private String documentNumber;
	private Date birthDate;
	private Date bondingDate;
	private String position;
	private Double salary;

	public UserDto() {

	}

	public UserDto(long id, String name, String lastName, String typeDocument, String documentNumber, Date birthDate,
			Date bondingDate, String position, Double salary) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.typeDocument = typeDocument;
		this.documentNumber = documentNumber;
		this.birthDate = birthDate;
		this.bondingDate = bondingDate;
		this.position = position;
		this.salary = salary;
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
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTypeDocument() {
		return typeDocument;
	}
	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public Date getBondingDate() {
		return bondingDate;
	}
	public void setBondingDate(Date bondingDate) {
		this.bondingDate = bondingDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
