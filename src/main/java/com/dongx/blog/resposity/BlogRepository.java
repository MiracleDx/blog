package com.dongx.blog.resposity;

import com.dongx.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * BlogRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-03 17:01
 * Modified by:
 */
public interface BlogRepository extends JpaRepository<Blog, String> {

	/**
	 * 通过类别查找博客
	 * @param category
	 * @return
	 */
	List<Blog> findByCategoryAndStatus(String category, Integer status);

	/**
	 * 查询所有博客
	 * @param status
	 * @return
	 */
	List<Blog> findAllByStatusOrderByCreateTimeDesc(Integer status);

	/**
	 * 查询该用户所有博客
	 * @param userId
	 * @param status
	 * @return
	 */
	List<Blog> findAllByCreateUserAndStatusOrderByCreateTimeDesc(String userId, Integer status);
}
