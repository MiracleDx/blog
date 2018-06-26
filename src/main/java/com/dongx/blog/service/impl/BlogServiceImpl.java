package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.entity.Blog;
import com.dongx.blog.entity.User;
import com.dongx.blog.entity.UserInfo;
import com.dongx.blog.resposity.BlogRepository;
import com.dongx.blog.resposity.UserInfoRepository;
import com.dongx.blog.resposity.UserRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.FtpUtil;
import com.dongx.blog.utils.GeneratorKeyUtil;
import com.dongx.blog.utils.UserUtil;
import com.dongx.blog.vo.BlogVo;
import com.dongx.blog.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;

/**
 * BlogServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-03 17:03
 * Modified by:
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
	
	@Resource
	private BlogRepository blogRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserInfoRepository userInfoRepository;

	@Override
	public ServerResponse findOne(String blogId) {
		
		Blog blog = blogRepository.getOne(blogId);
		
		User user = userRepository.getOne(blog.getCreateUser());
		UserVo userVo = null;
		if (user.getStatus() != CommonStatus.UNACTIVE.getCode()) {
			UserInfo userInfo = userInfoRepository.findUserInfoByUserId(user.getId());
			userVo = new UserVo();
			BeanUtils.copyProperties(userInfo, userVo);
			userVo.setId(user.getId());
			userVo.setUsername(user.getUsername());
			String defaultAvatar = "/static/images/avatars/user2.jpg";
			if (StringUtils.isEmpty(userVo.getAvatar())) {
				userVo.setAvatar(defaultAvatar);
			}
		}
		
		if (blog != null) {
			BlogVo blogVo = new BlogVo();
			BeanUtils.copyProperties(blog, blogVo);
			blogVo.setContent(this.readFile(blog.getAddress(), blog.getFilename()));
			Map<String, Object> map = new HashMap<>();
			map.put("user", userVo);
			map.put("blog", blogVo);
			return ServerResponse.createBySuccess(map);
		}
		return ServerResponse.createByError("该博客不存在");
	}

	@Override
	public ServerResponse findAll() {
		
		List<Blog> blogs = blogRepository.findAllByStatus(CommonStatus.ACTIVE.getCode());
		
		List<BlogVo> blogVos = new ArrayList<>();
		BlogVo vo = null;
		for (Blog blog : blogs) {
			vo =  new BlogVo();
			BeanUtils.copyProperties(blog, vo);
			blogVos.add(vo);
		}
		
		// 按照发表时间排序
		blogVos.stream().sorted(Comparator.comparing(BlogVo::getCreateTime).reversed());
		return ServerResponse.createBySuccess(blogVos);
	}

	@Override
	public ServerResponse findByCategory(String category) {
		return ServerResponse.createBySuccess(blogRepository.findByCategoryAndStatus(category, CommonStatus.ACTIVE.getCode()));
	}

	@Override
	@Transactional
	public ServerResponse update(BlogDTO blogDTO) {
		
		JwtUser user = UserUtil.getUser();
		
		if (blogDTO == null) {
			return null;
		}
		
		// 删除之前保存的文件
		Blog oldBlog = blogRepository.getOne(blogDTO.getId());
		boolean deleteResult = this.deleteFile(oldBlog.getAddress(), oldBlog.getFilename());

		if (!deleteResult) {
			return ServerResponse.createByError("更新失败，请稍后重新尝试更新");
		}
		
		// 保存文章内容
		String content = blogDTO.getContent();
		// 向FTP服务器上传获取文件路径以及文件名
		Map<String, String> resultMap = this.uploadFile(content, user.getId());

		if (resultMap != null) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			Date now = Date.from(Instant.now());
			
			blog.setAddress(resultMap.get("filePath"));
			blog.setFilename(resultMap.get("fileName"));
			blog.setUpdateUser(user.getId());
			blog.setUpdateTime(now);
			Blog result = blogRepository.save(blog);
			if (result != null) {
				BlogVo vo = new BlogVo();
				BeanUtils.copyProperties(result, vo);
				vo.setContent(content);
				log.info("博客更新成功：{}", blog.getTitle());
				return ServerResponse.createBySuccess("更新成功", vo);
			}
		}
		return ServerResponse.createByError("更新失败");
	}

	@Override
	@Transactional
	public ServerResponse save(BlogDTO blogDTO) {
		JwtUser user = UserUtil.getUser();
		
		if (blogDTO == null) {
			return null;
		}
		
		// 保存文章内容
		String content = blogDTO.getContent();
		// 向FTP服务器上传获取文件路径以及文件名
		Map<String, String> resultMap = this.uploadFile(content, user.getId());
		
		if (resultMap != null) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			
			Date now = Date.from(Instant.now());
			blog.setId(GeneratorKeyUtil.getInstance().generatorKey("blog"));
			blog.setAddress(resultMap.get("filePath"));
			blog.setFilename(resultMap.get("fileName"));
			blog.setCreateUser(user.getId());
			blog.setCreateTime(now);
			blog.setUpdateUser(user.getId());
			blog.setUpdateTime(now);
			blog.setStatus(CommonStatus.ACTIVE.getCode());
			Blog result = blogRepository.save(blog);
			if (result != null) {
				BlogVo vo = new BlogVo();
				BeanUtils.copyProperties(result, vo);
				vo.setContent(content);
				log.info("博客保存成功：{}", blog.getTitle());
				return ServerResponse.createBySuccess("保存成功", vo);
			}
		} 
		return ServerResponse.createByError("保存失败");
	}

	@Override
	public ServerResponse delete(BlogDTO blogDTO) {
		Blog blog = blogRepository.getOne(blogDTO.getId());
		blog.setStatus(CommonStatus.UNACTIVE.getCode());
		Blog result = blogRepository.save(blog);
		if (result != null) {
			return ServerResponse.createBySuccess("删除成功");
		}
		return ServerResponse.createByError("删除失败");
	}
	
	/**
	 * 博客正文上传方法 存入文件 返回文件存储路径
	 * @param content 博客正文
	 * @param userid 用户id
	 * @return
	 */
	private Map<String, String> uploadFile(String content, String userid) {
		FtpUtil ftpUtil = new FtpUtil();
		String filePath = "/blog/" + userid;
		String fileName = userid + String.valueOf(System.currentTimeMillis());
		boolean result = ftpUtil.uploadContent(filePath, fileName, content);
		if (result) {
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("filePath", filePath);
			resultMap.put("fileName", fileName);
			return resultMap;
		}
		return null;
	}

	/**
	 * 读取文件中的博文信息
	 * @param address 存储的路径
	 * @param fileName 存储的文件名
	 * @return
	 */
	private String readFile(String address, String fileName) {
		FtpUtil ftpUtil = new FtpUtil();
		String content = ftpUtil.readContent(address , fileName);
		return content;
	}

	/**
	 * 删除博客文件
	 * @param address 存储路径
	 * @param fileName 存储文件名
	 * @return
	 */
	private boolean deleteFile(String address, String fileName) {
		FtpUtil ftpUtils = new FtpUtil();
		return ftpUtils.deleteFile(address, fileName);
		 
	}
}
