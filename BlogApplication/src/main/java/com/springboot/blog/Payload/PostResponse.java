package com.springboot.blog.Payload;

import java.util.List;

import com.springboot.blog.DTO.PostDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	private int pageNo;
	private int pageSize;
	private long totalElement;
	private int totalPages;
	private boolean last;
	private List<PostDTO> content;

}
