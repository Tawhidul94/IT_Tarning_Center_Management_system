package com.boot.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Grade {


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long gradeId;
	
	private double grade;
	
	@ManyToOne(cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="course_Id")
	private Course theCourse;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name="student_Id")
	private Student theStudent;
	
	
	

	public Grade() {
		
	}




	public Grade(double grade) {
		super();
		this.grade = grade;
	}




	public double getGrade() {
		return grade;
	}




	public void setGrade(double grade) {
		this.grade = grade;
	}




	public long getGradeId() {
		return gradeId;
	}




	public void setGradeId(long gradeId) {
		this.gradeId = gradeId;
	}




	public Course getTheCourse() {
		return theCourse;
	}




	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}




	public Student getTheStudent() {
		return theStudent;
	}




	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	
	
	
}
