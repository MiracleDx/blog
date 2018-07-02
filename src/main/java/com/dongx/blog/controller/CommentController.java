package com.dongx.blog.controller;

import com.dongx.blog.dto.CommentDTO;
import com.dongx.blog.service.CommentService;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * CommentController
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-28 20:34
 * Modified by:
 */
@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Resource
	private CommentService commentService;
	
	@PostMapping("/save")
	public ServerResponse save(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
		return commentService.save(commentDTO, request);
	}
	
	@GetMapping("/findAllByBlogId/{blogId}")
	public ServerResponse findAllByBlogId(@PathVariable String blogId) {
		return commentService.findAllByBlogId(blogId);
	}
	
	@DeleteMapping("/deleteByCommentId/{commentId}")
	public ServerResponse deleteByCommentId(@PathVariable String commentId) {
		return commentService.deleteByCommentId(commentId);
	}
}
