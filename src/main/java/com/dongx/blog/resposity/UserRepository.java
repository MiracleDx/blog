package com.dongx.blog.resposity;

import com.dongx.blog.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * @author: dongx
 * Description: 用户数据访问接口
 * Created in: 2018-05-26 19:33
 * Modified by:
 */
@CacheConfig(cacheNames = "demos")
public interface UserRepository extends JpaRepository<User, String> {
	
	User findUserByUsername(String username);
}
