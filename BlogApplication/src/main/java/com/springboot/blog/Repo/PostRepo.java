package com.springboot.blog.Repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.blog.Entity.Category;
import com.springboot.blog.Entity.Post;
import com.springboot.blog.Entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	Page<Post> findByUser(User user,Pageable pageable);
	Page<Post> findAllByCategory(Category category,Pageable pageable);
	List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like :title")
	List<Post> searchByTitle(@Param("title") String title);

	
}
