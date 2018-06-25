package com.dongx.blog.controller;

import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.sys.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * BlogController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-25 19:58
 * Modified by:
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/blog")
public class BlogController {
	
	@Resource
	private BlogService blogService;
	
	@PostMapping("/save")
	public ServerResponse save(@RequestBody BlogDTO blogDTO) {
		return blogService.save(blogDTO);
	}
	
}
