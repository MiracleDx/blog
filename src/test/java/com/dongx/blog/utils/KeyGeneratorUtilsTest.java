package com.dongx.blog.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * KeyGeneratorUtilsTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-05-22 11:29
 * Modified by:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KeyGeneratorUtilsTest {

	@Test
	public void testKey()  {
		KeyGeneratorUtils keyUtil = KeyGeneratorUtils.getInstance();
		String result = keyUtil.generatorKey();
		String result2 = keyUtil.generatorKey("dongx");
		Assert.assertEquals(32, result.length());
		Assert.assertEquals(32, result2.length());
	}
}