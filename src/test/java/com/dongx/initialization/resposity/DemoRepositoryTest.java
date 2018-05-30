package com.dongx.initialization.resposity;

import com.dongx.blog.entity.Demo;
import com.dongx.blog.resposity.DemoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * DemoRepositoryTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-13 11:27
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoRepositoryTest {

	@Resource
	private DemoRepository demoRepository;
	
	@Test
	public void test() {
		
		// 创建4条记录
		Demo demo1 = new Demo();
		demo1.setName("1");
		demo1.setAge(1);

		Demo demo2 = new Demo();
		demo2.setName("2");
		demo2.setAge(2);

		Demo demo3 = new Demo();
		demo3.setName("3");
		demo3.setAge(3);

		Demo demo4 = new Demo();
		demo4.setName("4");
		demo4.setAge(4);
		
		demoRepository.save(demo1);
		demoRepository.save(demo2);
		demoRepository.save(demo3);
		demoRepository.save(demo4);
	}

	@Test
	public void findAll() {
		Assert.assertEquals(4, demoRepository.findAll().size());
	}

	@Test
	public void findByName() {
		Assert.assertEquals("1", demoRepository.findByName("1").getName());
		
	}

	@Test
	public void findByNameAndAge() {
		Assert.assertEquals("4", demoRepository.findByNameAndAge("4", 4).getName());
	}

	@Test
	public void findDemo() {
		Assert.assertEquals("3", demoRepository.findDemo("3").getName());
	}


}