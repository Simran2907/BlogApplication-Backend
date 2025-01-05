package com.springboot.blog.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.blog.Entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
