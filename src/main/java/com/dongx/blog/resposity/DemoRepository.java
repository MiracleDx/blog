package com.dongx.blog.resposity;

import com.dongx.blog.entity.Demo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * DemoRepository
 *
 * @author: dongx
 * Description: demo资源库-数据访问接口
 * Created in: 2018-05-13 11:23
 * Modified by:
 */
@CacheConfig(cacheNames = "demos")
public interface DemoRepository extends JpaRepository<Demo, Long> {
	
	@Cacheable(key = "#p0")
	Demo findByName(String name);
	
	Demo findByNameAndAge(String name, Integer age);
	
	@Query(value = "select * from gu_demo u where u.name = :name", nativeQuery = true)
	Demo findDemo(@Param("name") String name);
	
	@CachePut(key = "#p0.name")
	Demo save(Demo demo);
}
