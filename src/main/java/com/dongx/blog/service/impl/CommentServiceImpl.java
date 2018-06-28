package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.CommentDTO;
import com.dongx.blog.entity.Comment;
import com.dongx.blog.entity.User;
import com.dongx.blog.entity.UserInfo;
import com.dongx.blog.resposity.CommentRepository;
import com.dongx.blog.resposity.UserInfoRepository;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.CommentService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.KeyGeneratorUtils;
import com.dongx.blog.utils.UserUtils;
import com.dongx.blog.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private CommentRepository commentRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserInfoRepository userInfoRepository;
	
	@Override
	public ServerResponse save(CommentDTO commentDTO, HttpServletRequest request) {

		JwtUser user = UserUtils.getUser();
		
		if (commentDTO == null) {
			return null;
		}
		
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDTO, comment);
		comment.setId(KeyGeneratorUtils.getInstance().generatorKey("comment"));
		comment.setCreateTime(Date.from(Instant.now()));
		comment.setCreateUser(user.getId());
		comment.setStatus(CommonStatus.ACTIVE.getCode());
		Comment result = commentRepository.save(comment);
		
		if (result != null) {
			log.info("评论保存成功: {}", comment.getContent());
			return ServerResponse.createBySuccess("评论成功", comment);
		}
		log.info("评论保存失败: {}", commentDTO.getBlogId());
		return ServerResponse.createByError("评论失败， 请联系管理员");
	}
	
	@Override
	public ServerResponse findAllByBlogId(String blogId) {
		
		if (StringUtils.isEmpty(blogId)) {
			return null;	
		}

		// 查询所有评论
		List<Comment> comments = commentRepository.findAllByBlogIdAndStatusOrderByCreateTimeAsc(blogId, CommonStatus.ACTIVE.getCode());
		CommentVo vo = null;
		List<CommentVo> vos = new ArrayList<>();
		User user = null;
		UserInfo userInfo = null;
		String defaultAvatar = "/static/images/avatars/user2.jpg";
		for (Comment comment : comments) {
			vo = new CommentVo();
			BeanUtils.copyProperties(comment, vo);
			user = userRepository.getOne(comment.getCreateUser());
			userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
			vo.setUsername(user.getUsername());
			vo.setNickname(userInfo.getNickname());
			if (StringUtils.isEmpty(userInfo.getAvatar())) {
				vo.setAvatar(defaultAvatar);
			} else {
				vo.setAvatar(userInfo.getAvatar());
			}
			vos.add(vo);
		}

		return ServerResponse.createBySuccess(vos);
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
