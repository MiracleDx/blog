package com.dongx.blog.resposity;

import com.dongx.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

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
	List<Comment> findAllByBlogIdAndStatus(String blogId, Integer status);
}
