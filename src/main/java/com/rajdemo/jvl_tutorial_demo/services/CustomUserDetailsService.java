package com.rajdemo.jvl_tutorial_demo.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rajdemo.jvl_tutorial_demo.entity.UserEntity;
import com.rajdemo.jvl_tutorial_demo.repository.UserRepository;

@Service
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity user= userRepository.findByUsername(username)
		.orElseThrow(()-> new UsernameNotFoundException("User not found"));
		
		
		// TODO Auto-generated method stub
		return new User(user.getUsername(),user.getPassword(),Collections.singleton(new SimpleGrantedAuthority("USER_ROLE")));
	}

}
