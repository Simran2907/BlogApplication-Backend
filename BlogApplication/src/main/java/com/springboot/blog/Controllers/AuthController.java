
package com.springboot.blog.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.DTO.JWTRequest;
import com.springboot.blog.DTO.JWTResponse;
import com.springboot.blog.DTO.UserDTO;
import com.springboot.blog.ExceptionHandling.ApiException;
import com.springboot.blog.Security.CustomUserDetailService;
import com.springboot.blog.Security.JWTHelper;
import com.springboot.blog.Service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private CustomUserDetailService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTHelper jwtHelper;
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
		this.doAuthenticate(request.getEmail(),request.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
		String token = this.jwtHelper.generateToken(userDetails);
		JWTResponse jwtResponse = JWTResponse.builder().token(token).username(userDetails.getUsername()).build();
		return new ResponseEntity<JWTResponse>(jwtResponse,HttpStatus.OK);
		
	}

	private void doAuthenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
		try {
			authenticationManager.authenticate(authenticationToken);
		}catch(Exception e) {
			throw new ApiException("Invalid Username or passowrd !!!");			
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){
		UserDTO registeredUser = this.userService.registerUSer(userDTO);
		return new ResponseEntity<UserDTO>(registeredUser,HttpStatus.OK);
		
	}
	
	
}
