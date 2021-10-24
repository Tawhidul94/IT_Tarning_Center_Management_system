package com.boot;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.boot.entites.Admin;

import com.boot.repository.AdminRepository;
import com.boot.repository.CourseRepository;
import com.boot.repository.GradeRepository;
import com.boot.repository.StudentRepository;
import com.boot.repository.TeacherRepository;



@SpringBootApplication
public class ItTarningCenterManagementSystemApplication {
	
	@Autowired
	AdminRepository adRepo;
	
	@Autowired
	CourseRepository couRepo;
	
	@Autowired
	GradeRepository graRepo;
	
	@Autowired
	StudentRepository studRepo;
	
	@Autowired
	TeacherRepository teaRepo;
	

	public static void main(String[] args) {
		SpringApplication.run(ItTarningCenterManagementSystemApplication.class, args);
	}
		
		
		@Bean
		CommandLineRunner runner() {
			
			return args -> {
				
				//USERS
				Admin ad1 = new Admin("tawhid", "islam", "admin@gmail.com", "admin","01762719762","notun bazar ,Dhaka");
				
				
				adRepo.save(ad1);
			
				
				
			};
		}
}
				
			
			
	


