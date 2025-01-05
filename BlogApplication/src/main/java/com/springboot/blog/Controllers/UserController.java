package com.springboot.blog.Controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.DTO.UserDTO;
import com.springboot.blog.Payload.APIResponse;
import com.springboot.blog.Service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	public UserController(UserService userService) {
	    this.userService = userService;
	    System.out.println("UserService injected: " + (userService != null));
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return new ResponseEntity(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
		return new ResponseEntity(userService.createUser(userDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,@PathVariable int userId){
		return new ResponseEntity<>(userService.updateUser(userDTO, userId),HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<UserDTO>> getUserById(@PathVariable int userId){
		return new ResponseEntity(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable int userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity(new APIResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/currentUser")
	public String getCurrentUser(Principal principal){
		return principal.getName();
	}
	
	
	

}
