package com.boot.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boot.other.CourseSelector;



@Configuration
public class ManufacturingConfiguration {

	@Bean
	public CourseSelector newCourseSelector() {
		return new CourseSelector();
	}
}
