package com.boot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.repository.AdminRepository;
import com.boot.repository.GradeRepository;
import com.boot.repository.StudentRepository;
import com.boot.repository.TeacherRepository;
import com.boot.session.SessionHandler;
import com.boot.entites.Admin;
import com.boot.entites.Grade;
import com.boot.entites.Student;
import com.boot.entites.Teacher;

@Controller
@RequestMapping(value="/admin")
public class AdminController {

	
	private String portalType="ADMIN";	

	@Autowired
	AdminRepository adRepo;

	@Autowired
	StudentRepository studRepo;

	@Autowired
	TeacherRepository teachRepo;

	@Autowired
	GradeRepository gradRepo;

		@GetMapping
		public String mainPage(Model model) {
			if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
			Admin currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
			model.addAttribute("user", currentAdmin);
			
			List<Student> students = (List<Student>) studRepo.findAll();
			int amountOfStudents = students.size();
			model.addAttribute("studentAmount", amountOfStudents);
			
			List<Teacher> teachers = (List<Teacher>) teachRepo.findAll(); 
			int amountOfTeachers = teachers.size();
			model.addAttribute("teacherAmount", amountOfTeachers);
			
			List<Grade> grades = (List<Grade>) gradRepo.findAll();
			double totalGrade = 0;
			double count = 0;
			for (Grade grade : grades) {
				if(grade.getGrade() > 0.0) {
				totalGrade += grade.getGrade();
				count += 1;
				}
			}
			double averageGrade = totalGrade / count;
			model.addAttribute("averageGrade", averageGrade);
			
			return "admin-portal/admin-page-main.html";
		}	
		
		@GetMapping(value="/ManageAccount")
		public String manageAccount(Model model) {
			if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
			Admin currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
			model.addAttribute("admin", currentAdmin);
			return "admin-portal/admin-manage-account.html";
		}
		
		@PostMapping(value="/changePassword")
		public String changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String newPassword2) {
			if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();	
			Admin currentAdmin = adRepo.findById(SessionHandler.getSession().getUserId()).get();
			
			if ( (!currentAdmin.getPassword().equals(oldPassword)) || (!newPassword.equals(newPassword2)) ){
				return "admin-portal/admin-change-password-failed.html";
			} else {
				currentAdmin.setPassword(newPassword);
				adRepo.save(currentAdmin);
				return "admin-portal/admin-change-password-succeeded.html";
			}
		}
}
