package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.entity.Blog;
import com.dongx.blog.resposity.BlogRepository;
import com.dongx.blog.security.JwtUser;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.FtpUtils;
import com.dongx.blog.utils.GeneratorKeyUtil;
import com.dongx.blog.utils.UserUtil;
import com.dongx.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	@Override
	public ServerResponse findOne(String blogId) {
		
		Blog blog = blogRepository.getOne(blogId);
		
		if (blog != null) {
			BlogDTO blogDTO = new BlogDTO();
			BeanUtils.copyProperties(blog, blogDTO);
			blogDTO.setContent(this.readFile(blog.getAddress()));
			return ServerResponse.createBySuccess(blogDTO);
		}
		return ServerResponse.createByError("该博客不存在");
	}

	@Override
	public ServerResponse findAll() {
		return ServerResponse.createBySuccess(blogRepository.findAllByStatus(CommonStatus.ACTIVE.getCode()));
	}

	@Override
	public ServerResponse findByCategory(String category) {
		return ServerResponse.createBySuccess(blogRepository.findByCategoryAndStatus(category, CommonStatus.ACTIVE.getCode()));
	}

	@Override
	public ServerResponse update(BlogDTO blogDTO) {
		
		JwtUser user = UserUtil.getUser();
		
		if (blogDTO == null) {
			return null;
		}
		
		// 删除之前保存的文件
		Blog oldBlog = blogRepository.getOne(blogDTO.getId());
		this.deleteFile(oldBlog.getAddress());

		// 保存文章内容
		String content = blogDTO.getContent();
		Map<String, String> resultMap = this.uploadFile(content, user.getId());

		if (resultMap != null) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			blog.setAddress(resultMap.get("filePath"));
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
		Map<String, String> resultMap = this.uploadFile(content, user.getId());
		
		if (resultMap != null) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			
			Date now = Date.from(Instant.now());
			blog.setId(GeneratorKeyUtil.getInstance().generatorKey("blog"));
			blog.setAddress(resultMap.get("filePath"));
			blog.setFilename(resultMap.get("fileName"));
			blog.setCreateUser(user.getId());
			blog.setCrreateTime(now);
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
		FtpUtils ftpUtils = new FtpUtils();
		String filePath = "/blog/" + userid;
		String fileName = String.valueOf(System.currentTimeMillis());
		boolean result = ftpUtils.uploadContent(filePath, fileName, content);
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
	 * @param categroy
	 * @return
	 */
	private String readFile(String categroy) {
		return null;
	}

	/**
	 * 删除博客文件
	 * @param address
	 * @return
	 */
	private String deleteFile(String address) {
		return null;
	}
}
