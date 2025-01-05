package com.springboot.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.springboot.blog.Entity.User;
import com.springboot.blog.ExceptionHandling.ResourceNotFoundException;
import com.springboot.blog.Repo.UserRepo;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from database by user name
		User user = userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
		return user;
	}

}
