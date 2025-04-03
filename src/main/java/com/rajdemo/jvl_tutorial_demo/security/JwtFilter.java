package com.rajdemo.jvl_tutorial_demo.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajdemo.jvl_tutorial_demo.services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailsService csv;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeader=request.getHeader("Authorization");
		
		if(authHeader==null|| !authHeader.startsWith("Bearer")) {
			
			filterChain.doFilter(request, response);
			return;
			
		}
		String token=authHeader.substring(7);
		
		try {
			String username=jwtUtil.extractUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails=csv.loadUserByUsername(username);
				if(jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken=
							new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
			
		}
		catch(Exception e) {
			
			Map<String,String> responseMap=new HashMap<>();
			responseMap.put("erro", "Invalid Token");
			
			ObjectMapper objectMapper=new ObjectMapper();
			String jsonString=objectMapper.writeValueAsString(responseMap);
			
			
			
			response.getWriter().write(jsonString);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
			
		}
		filterChain.doFilter(request, response);
		//return;
	}

}
