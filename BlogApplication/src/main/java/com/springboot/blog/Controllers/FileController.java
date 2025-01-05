package com.springboot.blog.Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.blog.Payload.FileResponse;
import com.springboot.blog.Service.FileService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/file")
public class FileController {
	
	@Value("${project.image}")
	private String path;
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(@RequestParam("image") MultipartFile image){
		
		String fileName="";
		try {
			fileName = fileService.uploadImage(path, image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return new ResponseEntity<FileResponse>(new FileResponse(fileName, "Image is successfully uploaded"),HttpStatus.OK);
	}
	
	@GetMapping("/images/{imageName}")
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
