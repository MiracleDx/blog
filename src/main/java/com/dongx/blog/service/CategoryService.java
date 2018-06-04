package com.dongx.blog.service;

import com.dongx.blog.dto.CategoryDTO;
import com.dongx.blog.sys.ServerResponse;

/**
 * CategoryService
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 8:56
 * Modified by:
 */
public interface CategoryService {

	/**
	 * 保存或更新类别
	 * @param categoryDTO
	 * @return
	 */
	ServerResponse saveOrUpdate(CategoryDTO categoryDTO);

	/**
	 * 删除类别
	 * @param categoryDTO
	 * @return
	 */
	ServerResponse delete(CategoryDTO categoryDTO);

	/**
	 * 查询所有类别
	 * @return
	 */
	ServerResponse findAll();
}
