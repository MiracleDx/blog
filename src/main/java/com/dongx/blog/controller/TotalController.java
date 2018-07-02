package com.dongx.blog.controller;

import com.dongx.blog.entity.Total;
import com.dongx.blog.service.TotalService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TotalController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-02 21:39
 * Modified by:
 */
@RestController
@RequestMapping("/total")
public class TotalController {
	
	@Resource
	private TotalService totalService;
	
	@GetMapping("/addStatus/{blogId}")
	public ServerResponse addStatus(@PathVariable String blogId) {
		return totalService.addStatus(blogId);
	}

	@GetMapping("/cancelStatus/{blogId}")
	public ServerResponse cancelStatus(@PathVariable String blogId) {
		return totalService.cancelStatus(blogId);
	}
}
