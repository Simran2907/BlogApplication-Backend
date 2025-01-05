package com.springboot.blog.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
