package com.springboot.blog.DTO;

import java.util.Date;
import java.util.Set;

import com.springboot.blog.Payload.CommentDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
	
	private int id;

	@NotEmpty
	@NotNull
	private String title;
	
	@NotNull
	@NotEmpty
	private String content;
	
	private String imageName;
	private Date createDate;
	private UserDTO user;
	private CategoryDTO category;
	private Set<CommentDTO> comments;
	
	
	
}
