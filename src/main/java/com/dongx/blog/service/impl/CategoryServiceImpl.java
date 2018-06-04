package com.dongx.blog.service.impl;

import com.dongx.blog.common.CommonStatus;
import com.dongx.blog.dto.CategoryDTO;
import com.dongx.blog.entity.Category;
import com.dongx.blog.resposity.CategoryRepository;
import com.dongx.blog.service.CategoryService;
import com.dongx.blog.sys.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CategoryServiceImpl
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 8:57
 * Modified by:
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Resource
	private CategoryRepository categoryRepository;
	
	@Override
	public ServerResponse saveOrUpdate(CategoryDTO categoryDTO) {
		
		if (categoryDTO == null) {
			return null;
		}

		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);
		if (category.getId() == null) {
			category.setStatus(CommonStatus.ACTIVE.getCode());
		}
		Category result = categoryRepository.save(category);
		if (result != null) {
			log.info("保存/更新成功：{}", result);
			return ServerResponse.createBySuccess("保存/更新成功", result);
		}

		log.info("保存/更新失败：{}", result);
		return ServerResponse.createByError("保存/更新失败");
	}

	@Override
	public ServerResponse delete(CategoryDTO categoryDTO) {
		
		if (categoryDTO == null) {
			return null;
		}

		Category category = new Category();
		BeanUtils.copyProperties(categoryDTO, category);
		category.setStatus(CommonStatus.UNACTIVE.getCode());
		Category result = categoryRepository.save(category);
		
		if (result != null) {
			log.info("删除成功：{}", result);
			return ServerResponse.createBySuccess("删除成功");
		}

		log.info("删除失败：{}", result);
		return ServerResponse.createByError("删除失败");
	}

	@Override
	public ServerResponse findAll() {

		List<Category> resultList = categoryRepository.findAllByStatus(CommonStatus.ACTIVE.getCode());
		if (resultList != null && resultList.size() > 0) {
			return ServerResponse.createBySuccess(resultList);
		}
		
		return ServerResponse.createByError("暂无分类信息");
	}
}
