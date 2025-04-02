package com.rajdemo.jvl_tutorial_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rajdemo.jvl_tutorial_demo.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
	
	

}
