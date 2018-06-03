package com.dongx.blog.mapper;

import com.dongx.blog.entity.Permission;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

/**
 * PermissionMapperTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-27 19:28
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionMapperTest {
	
	@Resource
	private PermissionMapper permissionMapper;

	@Test
	public void findAll() {
		List<Permission> permissionList = permissionMapper.findAll();
		Assert.assertEquals(2, permissionList.size());
	}

	@Test
	public void findByUserid() {
		List<Permission> permissionList = permissionMapper.findByUserid("123213");
		Assert.assertEquals(3, permissionList.size());
	}
}