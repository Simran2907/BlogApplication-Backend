package com.springboot.blog.Service;

import java.util.List;

import com.springboot.blog.DTO.CategoryDTO;

public interface CategoryService {
	
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	CategoryDTO updateCategory(CategoryDTO categoryDTO,int categoryId);
	
	void deleteCategory(int categoryId);
	
	CategoryDTO getCategory(int categoryId);
	
	List<CategoryDTO> getCategories();

}
