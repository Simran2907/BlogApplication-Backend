package com.springboot.blog.Service.Impl;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.DTO.PostDTO;
import com.springboot.blog.Entity.Category;
import com.springboot.blog.Entity.Post;
import com.springboot.blog.Entity.User;
import com.springboot.blog.ExceptionHandling.ResourceNotFoundException;
import com.springboot.blog.Payload.PostResponse;
import com.springboot.blog.Repo.CategoryRepo;
import com.springboot.blog.Repo.PostRepo;
import com.springboot.blog.Repo.UserRepo;
import com.springboot.blog.Service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDTO createPost(PostDTO postDTO, int userId, int categoryId) {
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException(" user", "userId",userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId",categoryId));
		
		Post post = mapper.map(postDTO, Post.class);
		post.setImageName("default.png");
		post.setCreateDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost = postRepo.save(post);
		return mapper.map(newPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		post.setContent(postDTO.getContent());
		post.setTitle(postDTO.getTitle());
		post.setImageName(postDTO.getImageName());
		Post updatedPost = postRepo.save(post);
		return mapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize,String sortBy,String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p= PageRequest.of(pageNo, pageSize,sort);				
		Page<Post> pagePosts =  postRepo.findAll(p);
		
		List<Post> posts = pagePosts.getContent();
		List<PostDTO> postDTOs = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return getPostResponse(pagePosts, postDTOs);
	}

	@Override
	public PostDTO getPostById(int postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		return mapper.map(post, PostDTO.class);
	}

	@Override
	public PostResponse getPostByCategory(int catId,int pageNo,int pageSize) {
		Category category = categoryRepo.findById(catId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", catId));
		Pageable p = PageRequest.of(pageNo, pageSize);
		Page<Post> pagePosts = postRepo.findAllByCategory(category, p);
		List<Post> posts = pagePosts.getContent();
		List<PostDTO>  postsToSent = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());		
		return getPostResponse(pagePosts,postsToSent);
	}
	private PostResponse getPostResponse(Page<Post> pagePosts, List<PostDTO> postsToSent) {
		PostResponse postResponse = new PostResponse();
		postResponse.setPageNo(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElement(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLast(pagePosts.isLast());
		postResponse.setContent(postsToSent);
		return postResponse;
	}

	@Override
	public PostResponse getPostByUser(int userId,int pageNo,int pageSize) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		Pageable p = PageRequest.of(pageNo, pageSize);
		Page<Post> pagePosts = postRepo.findByUser(user, p);
		List<Post> posts = pagePosts.getContent();
		List<PostDTO> postsToSent =  posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return getPostResponse(pagePosts, postsToSent);
	
	}

	@Override
	public List<PostDTO> searchPost(String keyword) {
		List<Post> posts  = postRepo.searchByTitle("%"+keyword);
		List<PostDTO> postDTOs = posts.stream().map(post -> mapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDTOs;
	}

}
