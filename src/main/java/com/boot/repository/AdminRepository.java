package com.boot.repository;

import org.springframework.data.repository.CrudRepository;

import com.boot.entites.Admin;



public interface AdminRepository extends CrudRepository<Admin, Long> {

}
