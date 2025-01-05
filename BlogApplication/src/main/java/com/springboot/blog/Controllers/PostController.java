package com.springboot.blog.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blog.Config.AppConstant;
import com.springboot.blog.DTO.PostDTO;
import com.springboot.blog.Payload.APIResponse;
import com.springboot.blog.Payload.PostResponse;
import com.springboot.blog.Service.FileService;
import com.springboot.blog.Service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO, @PathVariable int userId,@PathVariable int categoryId){
		
		PostDTO postDTO2 = postService.createPost(postDTO, userId, categoryId);
		return new ResponseEntity<PostDTO>(postDTO2,HttpStatus.CREATED);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,@PathVariable int postId){
		PostDTO postDTO2 = postService.updatePost(postDTO,postId);
		return new ResponseEntity<PostDTO>(postDTO2,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable int userId,@RequestParam(value ="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required =false) int pageNo,
			@RequestParam(value ="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize){
		return new ResponseEntity<PostResponse>(postService.getPostByUser(userId,pageNo,pageSize),HttpStatus.FOUND);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable int categoryId,@RequestParam(value ="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required =false) int pageNo,
			@RequestParam(value ="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize){
		return new ResponseEntity<PostResponse>(postService.getPostByCategory(categoryId,pageNo,pageSize),HttpStatus.FOUND);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse > getAllPost(@RequestParam(value ="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required =false) int pageNo,
			@RequestParam(value ="pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstant.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir){
		return new ResponseEntity<PostResponse>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.FOUND);
	}		
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable int postId){
		return new ResponseEntity<PostDTO>(postService.getPostById(postId),HttpStatus.FOUND);
	}	
	
	
	@DeleteMapping("/post/{postId}")
	public APIResponse deletePost(@PathVariable int postId){
		postService.deletePost(postId);
		return new APIResponse("Post is successfully deleted", true);
	}	
	
	@PostMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keywords){
		return new ResponseEntity<List<PostDTO>>(postService.searchPost(keywords),HttpStatus.FOUND);
	}
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable int postId) throws IOException{ 
		String fileName = fileService.uploadImage(path, image);
		PostDTO postDTO = postService.getPostById(postId);
		postDTO.setImageName(fileName);
		return new ResponseEntity<PostDTO>(postService.updatePost(postDTO, postId),HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws FileNotFoundException {
			InputStream is = fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			try {
				StreamUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}


}
