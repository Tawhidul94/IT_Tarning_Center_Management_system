package com.boot.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boot.entites.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
	public Course getByCourseName(String courseName);

}
