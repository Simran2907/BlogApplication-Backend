package com.springboot.blog.Service;

import org.springframework.stereotype.Service;

import com.springboot.blog.Payload.CommentDTO;

@Service
public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO, int postId,int userId);
	CommentDTO updateComment(CommentDTO commentDTO, int commentId);
	void deleteComment(int commentId);

}
