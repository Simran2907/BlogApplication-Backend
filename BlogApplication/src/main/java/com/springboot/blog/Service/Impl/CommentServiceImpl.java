package com.springboot.blog.Service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.blog.Entity.Comment;
import com.springboot.blog.Entity.Post;
import com.springboot.blog.Entity.User;
import com.springboot.blog.ExceptionHandling.ResourceNotFoundException;
import com.springboot.blog.Payload.CommentDTO;
import com.springboot.blog.Repo.CommentRepo;
import com.springboot.blog.Repo.PostRepo;
import com.springboot.blog.Repo.UserRepo;
import com.springboot.blog.Service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, int postId,int userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));

		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		Comment comment = mapper.map(commentDTO, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		
		Comment savedComment = commentRepo.save(comment);
		return mapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public CommentDTO updateComment(CommentDTO commentDTO, int commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "commentId",commentId ));
		comment.setComment(commentDTO.getComment());
	
		Comment savedComment = commentRepo.save(comment);
		return mapper.map(savedComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "commentId",commentId ));
		commentRepo.delete(comment);

	}

}
