package com.springboot.blog.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.Config.AppConstant;
import com.springboot.blog.DTO.UserDTO;
import com.springboot.blog.Entity.Role;
import com.springboot.blog.Entity.User;
import com.springboot.blog.ExceptionHandling.ResourceNotFoundException;
import com.springboot.blog.Repo.RoleRepo;
import com.springboot.blog.Repo.UserRepo;
import com.springboot.blog.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = populateDTOtoEntity(userDTO);
		User savedUser = userRepo.save(user);
		return populateEntityToDTO(savedUser);
	}

	private User populateDTOtoEntity(UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setAbout(userDTO.getAbout());
//		user.setPassword(userDTO.getPassword());
		return user;
	}
	
	private UserDTO populateEntityToDTO(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setAbout(user.getAbout());
//		userDTO.setPassword(user.getPassword());
		return userDTO;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, int userId) {
		User user  = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updatedUser  = userRepo.save(user);
		return  populateEntityToDTO(user);				
	}

	@Override
	public UserDTO getUserById(int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ",userId));
		return populateEntityToDTO(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDTO> userDTOs = users.stream().map(user -> populateEntityToDTO(user)).collect(Collectors.toList());
		return userDTOs;
	}

	@Override
	public UserDTO registerUSer(UserDTO userDTO) {
		User user = modelMapper.map(userDTO,User.class);
		
		//encoded Password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		//roles assigning
		Role role  = roleRepo.findById(AppConstant.NORMAL_USER).get();
		user.getRoles().add(role);
		
		User savedUser = userRepo.save(user);
		return modelMapper.map(savedUser,UserDTO.class);
	}

	@Override
	public void deleteUser(int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " id ",userId));
		userRepo.delete(user);
	}

}
