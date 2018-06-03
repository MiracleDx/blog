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
	
	List<Blog> findByCategory(String category); 
	
	List<Blog> findAllByStatus(Integer status);
}
