package com.springboot.blog.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.Payload.APIResponse;
import com.springboot.blog.Payload.CommentDTO;
import com.springboot.blog.Service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("user/{userId}/post/{postId}")
	public ResponseEntity<CommentDTO> createComment(@PathVariable int userId,@PathVariable int postId,@RequestBody CommentDTO commentDTO){
		CommentDTO commentDTO2 = commentService.createComment(commentDTO, postId,userId);
		return new ResponseEntity<CommentDTO>(commentDTO2, HttpStatus.OK);
	}
	
	@PutMapping("/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable int commentId,@RequestBody CommentDTO commentDTO){
		CommentDTO commentDTO2 = commentService.updateComment(commentDTO,commentId);
		return new ResponseEntity<CommentDTO>(commentDTO2, HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<APIResponse> deleteComment(@PathVariable int commentId){
		commentService.deleteComment(commentId);
		return new ResponseEntity<APIResponse>(new APIResponse("Comment Deleted Successfully", true), HttpStatus.OK);
	}

}
