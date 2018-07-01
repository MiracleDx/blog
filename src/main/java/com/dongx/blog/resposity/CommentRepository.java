package com.dongx.blog.resposity;

import com.dongx.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * CommentRepository
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-04 16:18
 * Modified by:
 */
public interface CommentRepository extends JpaRepository<Comment, String> {

	/**
	 * 通过博客id查找该博客下所有评论
	 * @param blogId
	 * @param status
	 * @return
	 */
	List<Comment> findAllByBlogIdAndStatusOrderByCreateTimeAsc(String blogId, Integer status);

	/**
	 * 通过博客id查找创建者在该博客下的所有评论
	 * @param blogId
	 * @param createuserId
	 * @param status
	 * @return
	 */
	List<Comment> findAllByBlogIdAndCreateUserAndStatus(String blogId, String createuserId, Integer status);

	/**
	 * 查找创建者的所有评论
	 * @param createuserId
	 * @param status
	 * @return
	 */
	List<Comment> findAllByCreateUserAndStatus(String createuserId, Integer status);

	/**
	 * 该博客下的所有评论假删除
	 * @param blogId
	 * @return
	 */
	@Query(value = "update blog_comment set status = 0 where blog_id = :blogId", nativeQuery = true)
	@Modifying
	Integer deleteByBlogId(@Param("blogId") String blogId);

	/**
	 * 该评论下的所有评论假删除
	 * @param pid
	 * @return
	 */
	@Query(value = "update blog_comment set status = 0 where pid = :pid", nativeQuery = true)
	@Modifying
	Integer deleteByPid(@Param("pid") String pid);
}
