package com.boot.controller;

import java.util.ArrayList;
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
import com.boot.entites.Teacher;
import com.boot.other.CourseSelector;
import com.boot.repository.CourseRepository;
import com.boot.repository.GradeRepository;
import com.boot.repository.TeacherRepository;
import com.boot.session.SessionHandler;

@Controller
@RequestMapping(value="/teachers")
public class TeacherController {

private String portalType = "TEACHER";	
	
	@Autowired
	TeacherRepository teachRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@Autowired
	CourseSelector courseSelector;
	
	@GetMapping
	public String mainPage(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher currentTeacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("user", currentTeacher);
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(currentTeacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(currentTeacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(currentTeacher));
		return "teacher-portal/teacher-page-main.html";
	}

	@GetMapping(value="/ManageAccount")
	public String manageTeacherAccount(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("teacher", teacher);
		return "teacher-portal/teacher-manage-account.html";
	}
	
	@PostMapping(value="/changePassword")
	public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		
		if ( !(oldPassword.equals(teacher.getPassword())) || !(newPassword.equals(newPassword2)) ) {
			return "teacher-portal/teacher-change-password-failed.html";
		} else {
			teacher.setPassword(newPassword);
			teachRepo.save(teacher);
			return "teacher-portal/teacher-change-password-succeeded.html";
		}
	}
	
	@GetMapping(value="createNewCourse")
	public String createNewClass(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("course", new Course());
		return"teacher-portal/teacher-create-new-course.html";
	}
	
	@PostMapping(value="/saveCourse")
	public String saveCourse(Course newCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		newCourse.setTheTeacher(teacher);
		couRepo.save(newCourse);
		return"teacher-portal/teacher-course-saved.html";
	}
	
	@GetMapping(value="/manageCourses")
	public String manageCourses(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(teacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(teacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(teacher));
		return"teacher-portal/teacher-manage-courses";
	}
	@GetMapping(value="/manageCoursesprogress")
	public String manageCoursesProgress(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(teacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(teacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(teacher));
		return"teacher-portal/teacher-manage-courses1";
	}
	
	@GetMapping(value="/manageCoursesComplete")
	public String manageCoursescomplete(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(SessionHandler.getSession().getUserId()).get();
		model.addAttribute("notStarted", courseSelector.getCoursesNotStarted(teacher));
		model.addAttribute("inProgress", courseSelector.getCoursesInProgress(teacher));
		model.addAttribute("completed", courseSelector.getCoursesCompleted(teacher));
		return"teacher-portal/teacher-manage-courses2";
	}
	
	@GetMapping(value="/manageTheCourse")
	public String manageTheCourse(@RequestParam Long chosenCourse, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Course course = couRepo.findById(chosenCourse).get();
		model.addAttribute("chosenCourse", course);
		
		List<Student> enrolledStudents = course.getStudents();
		
		List<Grade> grades = new ArrayList<>();
		
		for (Student student : enrolledStudents) {
			for (Grade grade : student.getGrades()) {
				if (grade.getTheStudent() == student && grade.getTheCourse().getCourseId() == chosenCourse) {
					grades.add(grade);
				}
			}
		}
	
		model.addAttribute("grades", grades);
		return "teacher-portal/teacher-manage-chosen-course.html";
	}
	
	@PostMapping(value="/setGrade")
	public String setGrade(@RequestParam Long gradeId, @RequestParam Double courseGrade, @RequestParam Long chosenCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Grade grade = gradRepo.findById(gradeId).get();
		grade.setGrade(courseGrade);
		gradRepo.save(grade);
		return "redirect:/teachers/manageTheCourse?chosenCourse=" + chosenCourse;
	}
	

	
	@PostMapping(value="/saveCourseStage")
	public String saveCourseStage(@RequestParam String updateStage, @RequestParam Long chosenCourse) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Course course = couRepo.findById(chosenCourse).get();
		course.setStage(updateStage);
		couRepo.save(course);
		return "redirect:/teachers/manageTheCourse?chosenCourse=" + chosenCourse;
	}
}
