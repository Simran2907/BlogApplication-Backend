package com.springboot.blog.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	private int categoryId;
	
	@NotEmpty
	@Size(min =3,message = "Category title should be of length 3")
	private String categoryTitle;
	
	@NotEmpty
	private String categoryDescription;
	
}
