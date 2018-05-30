package com.dongx.initialization.utils;

import com.dongx.blog.utils.GeneratorKeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * GeneratorKeyUtilTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-22 11:29
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GeneratorKeyUtilTest {

	@Test
	public void testKey()  {
		GeneratorKeyUtil keyUtil = GeneratorKeyUtil.getInstance();
		String result = keyUtil.generatorKey();
		String result2 = keyUtil.generatorKey("dongx");
		Assert.assertEquals(32, result.length());
		Assert.assertEquals(32, result2.length());
	}
}