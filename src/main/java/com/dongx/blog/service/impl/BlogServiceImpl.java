package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.entity.Blog;
import com.dongx.blog.resposity.BlogRepository;
import com.dongx.blog.service.BlogService;
import com.dongx.blog.sys.ServerResponse;
import com.dongx.blog.utils.GeneratorKeyUtil;
import com.dongx.blog.vo.BlogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.Date;

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
	
	public ServerResponse findAll() {
		return ServerResponse.createBySuccess(blogRepository.findAllByStatus(CommonStatus.ACTIVE.getCode()));
	}
	
	public ServerResponse findByCategory(String category) {
		return ServerResponse.createBySuccess(blogRepository.findByCategory(category));
	}
	
	public ServerResponse update(BlogDTO blogDTO) {
		
		if (blogDTO == null) {
			return null;
		}
		
		// 删除之前保存的文件
		Blog oldBlog = blogRepository.getOne(blogDTO.getId());
		this.deleteFile(oldBlog.getAddress());

		// 保存文章内容
		String content = blogDTO.getContent();
		String address = this.uploadFile(content);

		if (StringUtils.isNotEmpty(address)) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			blog.setAddress(address);
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
	
	public ServerResponse save(BlogDTO blogDTO) {
		
		if (blogDTO == null) {
			return null;
		}
		
		// 保存文章内容
		String content = blogDTO.getContent();
		String address = this.uploadFile(content);
		
		if (StringUtils.isNotEmpty(address)) {
			Blog blog = new Blog();
			BeanUtils.copyProperties(blogDTO, blog);
			
			Date now = Date.from(Instant.now());
			blog.setId(GeneratorKeyUtil.getInstance().generatorKey("blog"));
			blog.setAddress(address);
			blog.setCrreateTime(now);
			blog.setUpdateTime(now);
			blog.setStatus(CommonStatus.ACTIVE.getCode());
			Blog result = blogRepository.save(blog);
			if (result != null) {
				BlogVo vo = new BlogVo();
				BeanUtils.copyProperties(result, vo);
				vo.setContent(content);
				log.info("博客保存成功：{}", blog.getTitle());
				return ServerResponse.createBySuccess("保存成功", blogDTO);
			}
		}
		return ServerResponse.createByError("保存失败");
	}

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
	 * @return
	 */
	private String uploadFile(String content) {
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
