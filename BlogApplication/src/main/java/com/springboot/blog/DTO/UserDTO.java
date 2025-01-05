package com.springboot.blog.DTO;

import java.util.HashSet;
import java.util.Set;

import com.springboot.blog.Payload.CommentDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

	private int id;
	
	@NotEmpty
	@Size(min =  4, message = "Name should be of minimum 4 character")
	private String name;
	
	@Email(message="Email address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min = 6 ,max = 10 ,message ="Password must be minimum of 3 Chars and maximum of 10 chars")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<CommentDTO> comments;
	
	private Set<RoleDTO> roles = new HashSet<>();
}
