package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.entites.Course;
import com.boot.entites.Grade;
import com.boot.entites.Student;
import com.boot.other.CourseSelector;
import com.boot.repository.CourseRepository;
import com.boot.repository.GradeRepository;
import com.boot.repository.StudentRepository;
import com.boot.session.SessionHandler;

@Controller
@RequestMapping(value="/students")
public class StudentController {
	private String portalType = "STUDENT";	

	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@Autowired
	CourseSelector courseSelector;

	@GetMapping
	public String mainPage(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Student currentStudent = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("user", currentStudent);
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(currentStudent));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(currentStudent));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(currentStudent));
		return "student-portal/student-page-main.html";
	}	
		//student enroll course
	@GetMapping(value="/ManageAccount")
	public String manageAccount(@RequestParam("enrollingCourse") String cName,Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("student", studRepo.findById(SessionHandler.getSession().getUserId()).get());
		
		return"student-portal/student-manage-account.html";
	}
	
	@PostMapping(value="changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Student student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		if( !(oldPassword.equals(student.getPassword())) || !(newPassword.equals(newPassword2))){
			return "student-portal/student-change-password-failed.html";
		} else {
			student.setPassword(newPassword);
			studRepo.save(student);
			return "student-portal/student-change-password-succeeded.html";
		}
	}
		
	@GetMapping(value="/manageCourses")
	public String manageCourses(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Student currentStudent = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		List<Course>availableCourses = courseSelector.getAvailableCourses(currentStudent);
		List<Course>enrolledCourses = courseSelector.getCoursesNotStarted(studRepo.findById(SessionHandler.getSession().getUserId()).get());
		model.addAttribute("availableCourses", availableCourses);
		model.addAttribute("enrolledCourses", enrolledCourses);
		return "student-portal/student-manage-courses.html";
	}
	
	@PostMapping(value="enrollForCourse")
	public String enrollForCourse(@RequestParam Long enrollingCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		
		Course course = couRepo.findById(enrollingCourse).get();
		Student student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		Grade grade = new Grade(0.0);
		grade.setTheCourse(course);
		grade.setTheStudent(student);
		
		student.getCourses().add(course);
		studRepo.save(student);
		couRepo.save(course);
		gradRepo.save(grade);
		
		return "redirect:/students/manageCourses";
	}
	
	@GetMapping(value="/displayGrades")
	public String displayGrades(Model model) {
	if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Student student = studRepo.findById(SessionHandler.getSession().getUserId()).get();
		List<Grade> grades = student.getGrades();
		model.addAttribute("grades", grades);
		return "student-portal/student-display-grades.html";
	}
}
