package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.entites.Teacher;
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>{

	Teacher findByTeacherId(Long id);

}
