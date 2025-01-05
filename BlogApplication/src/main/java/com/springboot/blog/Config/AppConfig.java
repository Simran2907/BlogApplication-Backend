package com.springboot.blog.Config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.blog.Security.CustomUserDetailService;

@Configuration
public class AppConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();		
	}
	
	
	   
 

	
	
}
