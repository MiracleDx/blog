package com.dongx.blog.controller;

import com.dongx.blog.service.es.EsBlogService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * EsBlogController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-10 21:57
 * Modified by:
 */
@RestController
@RequestMapping("/es")
public class EsBlogController {
	
	@Resource
	private EsBlogService esBlogService;
	
	@GetMapping("/findAll")
	public ServerResponse findAll() {
		return esBlogService.findAll();
	}
	
	@GetMapping("/findTopFive")
	public ServerResponse findTopFive() {
		return esBlogService.findTopFive();
	}
	
	@GetMapping("/findNewFive")
	public ServerResponse findNewFive() {
		return esBlogService.findNewFive();
	}
	
	@GetMapping("/findByKeyword")
	public ServerResponse findByKeyword(@RequestParam String keyword) {
		return esBlogService.findByKeyword(keyword);
	}
}
