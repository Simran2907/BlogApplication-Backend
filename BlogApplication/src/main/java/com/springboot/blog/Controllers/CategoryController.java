package com.springboot.blog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.DTO.CategoryDTO;
import com.springboot.blog.Service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
		CategoryDTO categoryDTO2 =categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(categoryDTO2, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateUser(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable int id){
		CategoryDTO categoryDTO2 = categoryService.updateCategory(categoryDTO,id);
		return new ResponseEntity<CategoryDTO>(categoryDTO2, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable int id){
		CategoryDTO categoryDTO = categoryService.getCategory(id);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable int id) {
		categoryService.deleteCategory(id);
	}
	
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<CategoryDTO>> getCategories(){
		List<CategoryDTO> categoryDTOs = categoryService.getCategories();
		return new ResponseEntity<List<CategoryDTO>>(categoryDTOs, HttpStatus.FOUND);
	}

}
