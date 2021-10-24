package com.boot.entites;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long adminId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	private String phone;
	private String address;
	
	
	public Admin() {
		
	}


	public Admin( String firstName, String lastName, String email, String password,
			String phone,String address) {
		super();
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
	}


	public long getAdminId() {
		return adminId;
	}


	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}
	

	
	

}
