package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.entity.Comment;
import com.dongx.blog.resposity.CommentRepository;
import com.dongx.blog.service.CommentService;
import com.dongx.blog.sys.ServerResponse;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * CommentServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 16:17
 * Modified by:
 */
public class CommentServiceImpl implements CommentService {
	
	@Resource
	CommentRepository commentRepository;
	
	public ServerResponse findAll(String blogId) {
		
		if (StringUtils.isEmpty(blogId)) {
			return null;	
		}

		List<Comment> result = commentRepository.findAllByBlogIdAndStatus(blogId, CommonStatus.ACTIVE.getCode());
		
		if (result != null) {
			return ServerResponse.createBySuccess(result);
		}
		
		return ServerResponse.createByError();
	}
}
