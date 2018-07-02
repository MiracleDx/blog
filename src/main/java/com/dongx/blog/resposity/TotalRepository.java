package com.dongx.blog.resposity;

import com.dongx.blog.entity.Total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * TotalRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-07-01 11:16
 * Modified by:
 */
public interface TotalRepository extends JpaRepository<Total, Integer> {

	/**
	 * 通过博客和用户id查找是否点赞
	 * @param blogId
	 * @param userId
	 * @return
	 */
	Total findByUserIdAndBlogId(String userId, String blogId);

	/**
	 * 取消点赞
	 * @param userId
	 * @param blogId
	 * @param status
	 * @return
	 */
	@Query(value = "update blog_total set status = ?1 where user_id = ?2 and blog_id = ?3", nativeQuery = true)
	@Modifying
	Integer cancel(Integer status, String userId, String blogId);
}
