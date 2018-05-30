package com.dongx.initialization.service.Impl;

import com.dongx.blog.entity.Demo2;
import com.dongx.blog.service.Impl.Demo2ServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Demo2ServiceImplTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-13 19:34
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Demo2ServiceImplTest {

	@Resource
	private Demo2ServiceImpl demo2Service;
	
	@Test
	public void insert() {
		Demo2 demo2 = new Demo2();
		demo2.setId(10L);
		demo2.setName("111");
		demo2.setAge(111);
		Assert.assertEquals("1", demo2Service.insert(demo2).toString());
	}
}