package com.springboot.blog.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.UserDTO;

public interface UserService {
	
	UserDTO registerUSer(UserDTO userDTO);
	UserDTO createUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO, int userId);
	UserDTO getUserById(int userId);
	List<UserDTO> getAllUsers();
	void deleteUser(int userId);
	

}
