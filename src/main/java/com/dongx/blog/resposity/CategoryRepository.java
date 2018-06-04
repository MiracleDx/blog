package com.dongx.blog.resposity;

import com.dongx.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * CategoryRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 8:56
 * Modified by:
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	/**
	 * 查询所有类别
	 * @param status
	 * @return
	 */
	List<Category> findAllByStatus(Integer status);
}
