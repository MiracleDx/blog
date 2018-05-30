package com.dongx.blog.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisGeneratorUtil
 *
 * @author: dongx
 * Description: mybatis逆向生成工具类
 * Created in: 2018-05-13 18:23
 * Modified by:
 */
public class MyBatisGeneratorUtil {
	
	public void generator() throws Exception{
		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		//指定 逆向工程配置文件
		File configFile = new File("src/main/resources/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);

	}

	public static void main(String[] args) throws Exception {
		try {
			MyBatisGeneratorUtil generatorSqlmap = new MyBatisGeneratorUtil();
			generatorSqlmap.generator();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
