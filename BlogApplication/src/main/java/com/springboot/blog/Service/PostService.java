package com.springboot.blog.Service;

import java.util.List;

import com.springboot.blog.DTO.PostDTO;
import com.springboot.blog.Payload.PostResponse;


public interface PostService {
	
	PostDTO createPost(PostDTO postDTO,int userId, int categoryId);
	
	PostDTO updatePost(PostDTO postDTO, int postId);
	
	void deletePost(int postId);
	
	PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir);
	
	PostDTO getPostById(int postId);
	
	PostResponse getPostByCategory(int catId,int pageNo,int pageSize);
	
	PostResponse getPostByUser(int userId,int pageNo,int pageSize);
	
	List<PostDTO> searchPost(String keyword);
	
	
}
