package com.rajdemo.jvl_tutorial_demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajdemo.jvl_tutorial_demo.entity.UserEntity;
import com.rajdemo.jvl_tutorial_demo.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtutil;
	
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody UserEntity user) {
		
		try {
			Authentication authentication= authManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
					
					UserDetails userDetails=(UserDetails)authentication.getPrincipal();
			
			String token=jwtutil.generateToken(userDetails);
			
			return ResponseEntity.ok(Map.of("token",token));
			
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error","Invalid Username Or password"));
		}
		
	}
	
	

}
