package com.boot.repository;

import org.springframework.data.repository.CrudRepository;

import com.boot.entites.Grade;

public interface GradeRepository extends CrudRepository<Grade, Long> {

}
