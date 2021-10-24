package com.boot.other;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.boot.entites.Course;
import com.boot.entites.Student;
import com.boot.entites.Teacher;
import com.boot.repository.CourseRepository;


public class CourseSelector {

	@Autowired
	CourseRepository couRepo;
	
	public CourseSelector() {
		
	}
	
	public List<Course> getCoursesNotStarted(Teacher teacher){
		List<Course> coursesNotStarted = new ArrayList<>();
		for (Course course : couRepo.findAll()) {
			if (course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("NOTSTARTED")) coursesNotStarted.add(course); 
		}
		return coursesNotStarted;
	}
	
	public List<Course> getCoursesInProgress(Teacher teacher){
		List<Course> coursesInProgress = new ArrayList<>();
		for (Course course : couRepo.findAll()) {
			if(course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("INPROGRESS")) coursesInProgress.add(course);
		}
		return coursesInProgress;
	}
	
	public List<Course> getCoursesCompleted(Teacher teacher){
		List<Course> coursesCompleted = new ArrayList<>();
		for (Course course : couRepo.findAll()) {
			if (course.getTheTeacher().getTeacherId() == teacher.getTeacherId()
				&& course.getStage().equals("COMPLETED")) coursesCompleted.add(course);
		}
		return coursesCompleted;
	}
	
	public List<Course> getCoursesNotStarted(Student student){
		List<Course> coursesNotStarted = new ArrayList<>();
		for (Course course : student.getCourses()) {
			if (course.getStage().equals("NOTSTARTED")) coursesNotStarted.add(course); 
		}
		return coursesNotStarted;
	}
	
	public List<Course> getCoursesInProgress(Student student){
		List<Course> coursesInProgress = new ArrayList<>();
		for (Course course : student.getCourses()) {
			if (course.getStage().equals("INPROGRESS")) coursesInProgress.add(course); 
		}
		return coursesInProgress;
	}
	
	public List<Course> getCoursesCompleted(Student student){
		List<Course> coursesCompleted = new ArrayList<>();
		for (Course course : student.getCourses()) {
			if (course.getStage().equals("COMPLETED")) coursesCompleted.add(course); 
		}
		return coursesCompleted;
	}
	
	public List<Course> getAvailableCourses(Student student){
		List<Course> availableCourses = new ArrayList<>();
		for (Course course : couRepo.findAll()) {
			if(course.getStage().equals("NOTSTARTED") && course.getMaxStudents() > course.getStudents().size() &&
				!(course.getStudents().contains(student))) { 
				availableCourses.add(course);
			}
		}
		return availableCourses;
	}

	
	
}
