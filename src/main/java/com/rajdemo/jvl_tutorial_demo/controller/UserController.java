 package com.rajdemo.jvl_tutorial_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajdemo.jvl_tutorial_demo.entity.UserEntity;
import com.rajdemo.jvl_tutorial_demo.exceptions.ResourceNotFoundException;
import com.rajdemo.jvl_tutorial_demo.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	@GetMapping
//	public String getUsers() {
//		return "HELLO API";
//	}
	
	
	@GetMapping
	public List<UserEntity> getUsers() {
		
		//return Arrays.asList(new User(1L,"RAJ","raj@gmail.com"),new User(2L,"RAHUL","rahul@gmail.com"),new User(3L,"Alice","Alice@gmail.com"));
		
		return userRepository.findAll();
		
	}
	
	@PostMapping
	public UserEntity createUser(@RequestBody UserEntity user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
		
	}
	
	@GetMapping("/{id}")
	public UserEntity getuserbyid(@PathVariable Long id) {
		
		return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with this id"+id));
		
		
	}
	
	@PutMapping("/{id}")
	public UserEntity updateuser(@PathVariable Long id,@RequestBody UserEntity user) {
		UserEntity userdata=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with this id"+id));
		userdata.setEmail(user.getEmail());
		userdata.setName(user.getName());
		return userRepository.save(userdata);
		
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteuser(@PathVariable Long id) {
		UserEntity userdata=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with this id"+id));
		userRepository.delete(userdata);
		return ResponseEntity.ok().build();
	}
	

}
