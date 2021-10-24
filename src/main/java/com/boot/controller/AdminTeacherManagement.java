package com.boot.controller;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.entites.Course;
import com.boot.entites.Grade;
import com.boot.entites.Student;
import com.boot.entites.Teacher;
import com.boot.repository.GradeRepository;
import com.boot.repository.TeacherRepository;
import com.boot.session.SessionHandler;

@Controller
@RequestMapping(value="/admin")
public class AdminTeacherManagement {

	private String portalType="ADMIN";	
		
	@Autowired
	TeacherRepository teachRepo;
	
	@Autowired
	GradeRepository gradRepo;
	
	@GetMapping(value="/ManageTeachers")
	public String manageTeachers(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		model.addAttribute("teacher", new Teacher());
		return "admin-portal/admin-manage-teachers.html";
	}
	
	@PostMapping(value="saveTeacher")
	public String saveTeacher(Teacher teacher, @RequestParam String password) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		teacher.setPassword(password);
		teachRepo.save(teacher);
		return "redirect:/admin/teacherSaved";
	}
	
	//teacher data saved
		
	@GetMapping(value="/teacherSaved")
	public String teacherSaved() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-saved.html";
	}
	
	//update for Teacher
	
	@RequestMapping("/teacherUpdate/{teacherId}")
	public String showEditStudentPage(@PathVariable(name="teacherId") Long id,Model m) {

		Teacher ttd=teachRepo.findByTeacherId(id);
		m.addAttribute("teacher",ttd);
		return "admin-portal/teacherUpdate";
	}


//	delete teacher
	
	@RequestMapping("/teacherDelete/{teacherId}")
	public String delete(@PathVariable("teacherId") Long teacherId) {
		
		teachRepo.deleteById(teacherId);
		
		return "admin-portal/admin-teacher-saved";
	}
	//find single teacher
	@GetMapping("/findteacher")
	public String find(@RequestParam("search") Long id,Model m){

		Teacher byId = this.teachRepo.getById(id);
		 m.addAttribute("fid",byId);
		 m.addAttribute("search","yes");
	
		 return "admin-portal/admin-all-teachers";
	}
	
	
	@PostMapping(value="/searchTeacher")
	public String searchTeacher(@RequestParam String firstName, @RequestParam String lastName) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		for (Teacher teacher : teachRepo.findAll()) {
			if (teacher.getFirstName().toLowerCase().equals(firstName.toLowerCase()) && 
					teacher.getLastName().toLowerCase().equals(lastName.toLowerCase())) {
					long teacherId = teacher.getTeacherId();
				return "redirect:/admin/foundTeacher/" + teacherId;
			}
		}
		return "redirect:/admin/TeacherNotFound";
	}
	
	@GetMapping(value="/TeacherNotFound")
	public String teacherNotFound() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-not-found.html";
	}
	
	@GetMapping(value="/foundTeacher/{teacherId}")
	public String foundTeacher(@PathVariable long teacherId, Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Optional<Teacher> teacher = teachRepo.findById(teacherId);
		if (teacher.isEmpty()) {
			return "redirect:/admin/TeacherNotFound";
		} else {
			model.addAttribute("teacher", teacher.get());
			List<Course> courses = teacher.get().getCourses();
			Map<String, Double> averageGrades = new HashMap<String, Double>();
			
			for(Course course : courses) {
				double totalOfGrades = 0.0;
				double count = 0.0;
				double averageGrade = 0.0;
				for (Grade grade : gradRepo.findAll() ) {
					if(course.getCourseId() == grade.getTheCourse().getCourseId() && grade.getGrade() > 0.0) {
						totalOfGrades += grade.getGrade();
						count+=1;
					}
					
				}	
				
				averageGrade = totalOfGrades / count;
				
				if(averageGrade > 0.0){
					averageGrades.put(course.getCourseName(), averageGrade);
				
				System.out.println("TESTING:" + course.getCourseName() + "GRADE" + averageGrade);
				}
				
				
				
				model.addAttribute("averageGrades", averageGrades);
			}	
		}	
		return	"admin-portal/admin-single-teacher.html";
	}
	
	@PostMapping(value = "/resetTeacherPassword")
	public String resetPassword(@RequestParam String password, @RequestParam long teacherId) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		Teacher teacher = teachRepo.findById(teacherId).get();
		teacher.setPassword(password);
		teachRepo.save(teacher);
		return "redirect:/admin/teacherPasswordReset";
	}
	
	@GetMapping(value = "/teacherPasswordReset")
	public String passwordIsReset() {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		return "admin-portal/admin-teacher-password-reset.html";
	}
	
	@GetMapping(value="/displayAllTeachers")
	public String displayAllTeachers(Model model) {
		if (SessionHandler.accessNotAllowed(portalType)) return SessionHandler.redirect();
		List<Teacher> allTeachers = (List<Teacher>) teachRepo.findAll();
		model.addAttribute("allTeachers", allTeachers);
		model.addAttribute("search","no");
		return "admin-portal/admin-all-teachers.html";
	}
}
