package com.dongx.blog.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * FtpUtilsTest
 *
 * @author: dongx
 * Description:
 * Created in: 2018-06-24 12:59
 * Modified by:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpUtilsTest {

	@Test
	public void test() {
		FtpUtils ftpUtils = new FtpUtils();
		try {
			InputStream input = new FileInputStream(new File("C:\\Users\\77542\\Desktop\\user.jpg"));
			ftpUtils.uploadFile("/images/test", "test.jpg", input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		FtpUtils ftpUtils = new FtpUtils();
		ftpUtils.uploadContent("/blog/test", "test.txt", "Hello World!!!");
	}
	
	@Test
	public void test3() {
		FtpUtils ftpUtils = new FtpUtils();
		System.out.println(ftpUtils.readContent("/blog/test", "test.txt"));
	}

	@Test
	public void test4() {
		FtpUtils ftpUtils = new FtpUtils();
		System.out.println(ftpUtils.deleteFile("/blog/test", "test.txt"));
	}
	
	

}