package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.CommentDTO;
import com.dongx.blog.entity.Comment;
import com.dongx.blog.entity.User;
import com.dongx.blog.entity.UserInfo;
import com.dongx.blog.mapper.CommentMapper;
import com.dongx.blog.mapper.TotalCountMapper;
import com.dongx.blog.resposity.CommentRepository;
import com.dongx.blog.resposity.UserInfoRepository;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.CommentService;
import com.dongx.blog.service.TotalService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.IpUtils;
import com.dongx.blog.utils.KeyGeneratorUtils;
import com.dongx.blog.utils.UserUtils;
import com.dongx.blog.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Server;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	private CommentMapper commentMapper;
	
	@Resource
	private TotalService totalService;
	
	@Value("${ftp.defaultAvatar}")
	private String defaultAvatar;
	
	@Value("${ftp.url}")
	private String ftpUrl;
	
	@Override
	public ServerResponse save(CommentDTO commentDTO, HttpServletRequest request) {

		JwtUser user = UserUtils.getUser();
		
		if (commentDTO == null) {
			return null;
		}
		
		String ip = IpUtils.getIpAddr(request);
		Comment comment = new Comment();
		BeanUtils.copyProperties(commentDTO, comment);
		
		// 如果是一级评论增加楼号
		Integer floor = null;
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", commentDTO.getBlogId());
		if (StringUtils.isNotEmpty(commentDTO.getPid())) {
			map.put("commentId", commentDTO.getPid());
		}
		floor = commentMapper.findMaxFloorByBlogId(map);
		
		comment.setId(KeyGeneratorUtils.getInstance().generatorKey("comment"));
		comment.setFloor(floor == null ? 1 : floor);
		comment.setCreateTime(Date.from(Instant.now()));
		comment.setCreateUser(user.getId());
		comment.setCreateIp(ip);
		comment.setStatus(CommonStatus.ACTIVE.getCode());
		
		// 回复自己 不记录回复人
		if (StringUtils.equals(comment.getReplyUserId(), user.getId())) {
			comment.setReplyUserId(null);
		}
		
		Comment result = commentRepository.save(comment);
		
		if (result != null) {
			totalService.addReplyCount(commentDTO.getBlogId());
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
		
		Map<String, Object> map = new HashMap<>();
		map.put("blogId", blogId);
		map.put("status", CommonStatus.ACTIVE.getCode());
		List<CommentVo> vos = commentMapper.findAllByBlogIdOrUserIdAndStatusOrder(map);
		vos.forEach(e -> {
			if (StringUtils.isEmpty(e.getAvatar())) {
				e.setAvatar(defaultAvatar);
			} else {
				e.setAvatar(ftpUrl + e.getAvatar());
			}
		});
		
		vos = this.findchildren(vos);
		
		log.info("查询博客下所有评论成功: {}", blogId);
		return ServerResponse.createBySuccess(vos);
	}

	private List<CommentVo> findchildren(List<CommentVo> vos) {
		
		List<CommentVo> fathers = vos.stream().filter(e -> StringUtils.isEmpty(e.getPid())).collect(Collectors.toList());
		List<CommentVo> childs = vos.stream().filter(e -> StringUtils.isNotEmpty(e.getPid())).collect(Collectors.toList());

		fathers.forEach(f -> {
			List<CommentVo> children = new ArrayList<>();
			childs.forEach(c -> {
				if (StringUtils.equals(f.getId(), c.getPid())) {
					children.add(c);
				}
			});
			f.setChildren(children);
		});
	
		return fathers;
	}
	

	@Override
	@Transactional
	public ServerResponse deleteByCommentId(String commentId) {
		
		JwtUser user = UserUtils.getUser();
		
		if (StringUtils.isEmpty(commentId)) {
			return null;
		}
		
		Comment comment = commentRepository.getOne(commentId);
		if (!StringUtils.equals(user.getId(), comment.getCreateUser())) {
			return ServerResponse.createByError("没有权限进行该操作");
		}
		comment.setStatus(CommonStatus.UNACTIVE.getCode());
		Comment result = commentRepository.save(comment);
		commentRepository.deleteByPid(result.getId());
		
		if (result != null) {
			log.info("评论删除成功: {}", comment.getId());
			return ServerResponse.createBySuccess("评论删除成功");
		}
		
		log.info("评论删除失败: {}", comment.getId());
		return ServerResponse.createByError("评论删除失败");
	}

	@Override
	public ServerResponse findAllCommentByUserId() {
		JwtUser user = UserUtils.getUser();
		
		Map<String, Object> map = new HashMap<>();
		map.put("userId", user.getId());
		map.put("status", CommonStatus.ACTIVE.getCode());
		List<CommentVo> commentVos = commentMapper.findAllByBlogIdOrUserIdAndStatusOrder(map);
		// 按创建时间逆序
		//commentVos.sort(Comparator.comparing(CommentVo::getCreateTime).reversed());
		return ServerResponse.createBySuccess("查询成功", commentVos);
	}
}

