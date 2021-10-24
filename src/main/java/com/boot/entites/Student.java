package com.boot.entites;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;


@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long studentId;
	
	private String firstName;
	
	private String email;
	private String password;
	
	private String phone;
	private String address;
	private long admitionFee;
	private long courseFee;
	
	@OneToMany(mappedBy="theStudent")
	private List<Grade> grades;
	
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, 
			fetch = FetchType.LAZY)
	@JoinTable(name="student_course", 
				joinColumns = @JoinColumn(name="student_id"),
				inverseJoinColumns =@JoinColumn(name="course_id"))
	private List<Course> courses;
	
	public Student() {
		
	}
	
	
	
	public List<Course> getCourses() {
		return courses;
	}



	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}



	public Student( Long studentId,String firstName, String email, String password,String phone,String address,long admitionFee) {
		super();
		this.studentId = studentId;
		this.firstName = firstName;
		
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address=address;
		this.admitionFee= admitionFee;
		
		
	}

	public  Long getStudentId() {
		return studentId;
	}

	public void setStudentId( Long studentId) {
		this.studentId = studentId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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



	public long getAdmitionFee() {
		return admitionFee;
	}



	public void setAdmitionFee(long admitionFee) {
		this.admitionFee = admitionFee;
	}



	public long getCourseFee() {
		return courseFee;
	}



	public void setCourseFee(long courseFee) {
		this.courseFee = courseFee;
	}



	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	

}
