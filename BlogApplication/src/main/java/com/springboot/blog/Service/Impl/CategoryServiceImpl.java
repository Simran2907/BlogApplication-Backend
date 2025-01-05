package com.springboot.blog.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.CategoryDTO;
import com.springboot.blog.Entity.Category;
import com.springboot.blog.ExceptionHandling.ResourceNotFoundException;
import com.springboot.blog.Repo.CategoryRepo;
import com.springboot.blog.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = mapper.map(categoryDTO, Category.class);
		categoryRepo.save(category);
		return mapper.map(category, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO,int categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId",categoryId));
		
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		categoryRepo.save(category);
		return mapper.map(category, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		categoryRepo.delete(category);
	}

	@Override
	public CategoryDTO getCategory(int categoryId) {
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId",categoryId));
		
		return mapper.map(category, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream().map(category -> mapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTOs;
	}

}
