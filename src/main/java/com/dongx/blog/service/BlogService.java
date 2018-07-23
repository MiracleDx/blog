package com.dongx.blog.service;

import com.dongx.blog.dto.BlogDTO;
import com.dongx.blog.sys.ServerResponse;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import javax.servlet.http.HttpServletRequest;

/**
 * BlogService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-03 14:39
 * Modified by:
 */
public interface BlogService {

	/**
	 * 查询一条博客
	 * @param blogId
	 * @return
	 */
	ServerResponse findOne(String blogId);

	/**
	 * 查询所有博客
	 * @return
	 */
	ServerResponse findAll();

	/**
	 * 根据分类查询博客
	 * @param category
	 * @return
	 */
	ServerResponse findByCategory(String category);

	/**
	 * 更新博客
	 * @param blogDTO
	 * @return
	 */
	ServerResponse update(BlogDTO blogDTO, HttpServletRequest request);

	/**
	 * 保存博客
	 * @param blogDTO
	 * @return
	 */
	ServerResponse save(BlogDTO blogDTO, HttpServletRequest request);

	/**
	 * 删除博客
	 * @param blogId
	 * @return
	 */
	ServerResponse delete(String blogId);

	/**
	 * 查找该用户下的所有博客
	 * @return
	 */
	ServerResponse findAllByUserId();
}
