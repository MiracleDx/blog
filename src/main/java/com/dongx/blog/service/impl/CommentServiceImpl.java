package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.CommentDTO;
import com.dongx.blog.entity.Comment;
import com.dongx.blog.resposity.CommentRepository;
import com.dongx.blog.service.CommentService;
import com.dongx.blog.sys.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

/**
 * CommentServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 16:17
 * Modified by:
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
	
	@Resource
	CommentRepository commentRepository;
	
	public ServerResponse saveByBlogId(CommentDTO commentDTO) {
		if (commentDTO == null) {
			return null;
		}
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDTO, comment);
		comment.setCreateTime(Date.from(Instant.now()));
		Comment result = commentRepository.save(comment);
		
		if (result != null) {
			log.info("评论保存成功: {}", comment.getContent());
			return ServerResponse.createBySuccess("评论保存成功", comment);
		}
		log.info("评论保存失败: {}", commentDTO.getBlogId());
		return ServerResponse.createByError("评论保存失败");
	}
	
	
	public ServerResponse findAllByBlogId(String blogId) {
		
		if (StringUtils.isEmpty(blogId)) {
			return null;	
		}
		return ServerResponse.createBySuccess(commentRepository.findAllByBlogIdAndStatus(blogId, CommonStatus.ACTIVE.getCode()));
	}
	
	public ServerResponse deleteByCommentId(CommentDTO commentDTO) {
		
		if (commentDTO == null) {
			return null;
		}

		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDTO, comment);
		comment.setStatus(CommonStatus.UNACTIVE.getCode());
		Comment result = commentRepository.save(comment);
		
		if (result != null) {
			log.info("评论删除成功: {}", comment.getId());
			return ServerResponse.createBySuccess("评论删除成功");
		}
		
		log.info("评论删除失败: {}", comment.getId());
		return ServerResponse.createByError("评论删除失败");
	}
	
	public ServerResponse findByBlogIdAndCreateUser(String BlogId, String createuserId) {
		return ServerResponse.createBySuccess(commentRepository.findAllByBlogIdAndCreateUserAndStatus(BlogId, createuserId, CommonStatus.ACTIVE.getCode()));
	}
	
	public ServerResponse findByCreateUser(String createuserId) {
		return ServerResponse.createBySuccess(commentRepository.findAllByCreateUserAndStatus(createuserId, CommonStatus.ACTIVE.getCode()));
	}
	
}
