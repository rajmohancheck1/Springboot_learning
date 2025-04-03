package com.rajdemo.jvl_tutorial_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class HomeController {
	
	@GetMapping
	 public String getHome() {
		 return "Welcome home";
	 }
	
	@GetMapping("/dashboard")
	 public String getDashBoard() {
		 return "Login succes";
	 }

}
