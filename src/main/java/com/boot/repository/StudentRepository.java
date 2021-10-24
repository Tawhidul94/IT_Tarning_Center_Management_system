package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.boot.entites.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	public Student findByStudentId(long id);

}
