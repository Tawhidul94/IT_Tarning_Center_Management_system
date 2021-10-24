package com.boot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.boot.entites.Grade;
import com.boot.entites.Student;
import com.boot.repository.StudentRepository;
import com.boot.session.SessionHandler;


@Controller
@RequestMapping(value="/admin")
public class AdminStudentManagement {

	private String portalType="ADMIN";	
	
	@Autowired
	StudentRepository studRepo;
	
	@GetMapping(value="/ManageStudents")
	public String manageStudents(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("student", new Student());
		return "admin-portal/admin-manage-students.html";
	}
	
	@PostMapping(value="/saveStudent")
	public String saveStudent(Student student, @RequestParam String password) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		student.setPassword(password);
		student.setCourseFee(0);
		studRepo.save(student);
		return "redirect:/admin/studentSaved";
	}
	//student data save
	@GetMapping(value="/studentSaved")
	public String studentSaved() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-saved.html";
	}
	
	//update student data
	
	@RequestMapping("/update/{studentId}")
	public String showEditStudentPage(@PathVariable(name="studentId") Long id,Model m) {
//		ModelAndView mav=new ModelAndView("new");
		Student std=studRepo.findByStudentId(id);
		m.addAttribute("student",std);
		return "admin-portal/studentUpdate";
	}


//	delete student 
	
	@RequestMapping("/delete/{studentId}")
	public String delete(@PathVariable("studentId") Long studentId) {
		
		studRepo.deleteById(studentId);
		
		return "admin-portal/admin-student-saved";
	}
	
	
	//show single student
	
	@GetMapping("/find")
	public String find(@RequestParam("search") Long id,Model m){
		 Student byId = this.studRepo.getById(id);		
		 m.addAttribute("fid",byId);
		 m.addAttribute("search","yes");
	
		 return "admin-portal/admin-all-students";
	}
		
	


	
	@GetMapping(value="/foundStudent/{studentId}")
	public String foundStudent(@PathVariable long studentId, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Optional<Student> student = studRepo.findById(studentId);
		if (student.isEmpty()) {
			return "redirect:/admin/studentNotFound";
		} else {
			model.addAttribute("student", student.get());
			
			List<Grade>grades = new ArrayList<>();
			for (Grade grade : student.get().getGrades()) {
				if (grade.getGrade() > 0.0) {
					grades.add(grade);
				}
			}
			model.addAttribute("grades", grades);
		}	
		return	"admin-portal/admin-single-student.html";
	}
	
	@GetMapping(value = "/studentNotFound")
	public String studentNotFound() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-not-found.html";
	}
	
	@PostMapping(value = "/resetStudentPassword")
	public String resetPassword(@RequestParam String password, @RequestParam long studentId) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Student student = studRepo.findById(studentId).get();
		student.setPassword(password);
		studRepo.save(student);
		return "redirect:/admin/studentPasswordReset";
	}
	
	@GetMapping(value = "/studentPasswordReset")
	public String passwordIsReset() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-student-password-reset.html";
	}
	
	@GetMapping(value="/displayAllStudents")
	public String displayAllStudents(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		List<Student> allStudents = (List<Student>) studRepo.findAll();
		model.addAttribute("allStudents", allStudents);
		
			
		
	return "admin-portal/admin-all-students.html";
	}
}
